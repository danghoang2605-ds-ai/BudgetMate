package com.example.savemoneytime.MainApplication.ViewModels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.savemoneytime.MainApplication.Repository.AppRepository;
import com.example.savemoneytime.model.ExpenseEntity;
import com.example.savemoneytime.model.RevenueEntity;
import com.example.savemoneytime.model.TransactionItem;
import com.example.savemoneytime.network.NewsArticle;
import com.example.savemoneytime.network.NewsResponse;
import com.example.savemoneytime.util.MonthChange;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BudgetViewModel extends AndroidViewModel {

    private final AppRepository repository;

    // Data for the selected month
    private final MutableLiveData<Long>    totalIncomeLive  = new MutableLiveData<>(0L);
    private final MutableLiveData<Long>    totalExpenseLive = new MutableLiveData<>(0L);
    private final MutableLiveData<Long>    balanceLive      = new MutableLiveData<>(0L);
    private final MutableLiveData<List<TransactionItem>> transactionsLive = new MutableLiveData<>(new ArrayList<>());

    // Lifetime totals across all months
    private final MutableLiveData<Long>    allTimeIncomeLive  = new MutableLiveData<>(0L);
    private final MutableLiveData<Long>    allTimeExpenseLive = new MutableLiveData<>(0L);

    private final MutableLiveData<List<NewsArticle>> newsLive = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<Boolean> newsLoadingLive = new MutableLiveData<>(false);
    private final MutableLiveData<String>  newsErrorLive   = new MutableLiveData<>();
    private final MutableLiveData<String>  saveStatusLive  = new MutableLiveData<>();
    // {current month net, previous month net}, for the "vs last month" pill
    private final MutableLiveData<long[]>  netComparisonLive = new MutableLiveData<>();

    public BudgetViewModel(@NonNull Application application) {
        super(application);
        repository = new AppRepository(application);
    }

    public LiveData<Long>                  getTotalIncome()     { return totalIncomeLive; }
    public LiveData<Long>                  getTotalExpense()    { return totalExpenseLive; }
    public LiveData<Long>                  getBalance()         { return balanceLive; }
    public LiveData<List<TransactionItem>> getTransactions()    { return transactionsLive; }

    public LiveData<Long>                  getAllTimeIncome()   { return allTimeIncomeLive; }
    public LiveData<Long>                  getAllTimeExpense()  { return allTimeExpenseLive; }

    public LiveData<List<NewsArticle>>     getNews()            { return newsLive; }
    public LiveData<Boolean>               getNewsLoading()     { return newsLoadingLive; }
    public LiveData<String>                getNewsError()       { return newsErrorLive; }
    public LiveData<String>                getSaveStatus()      { return saveStatusLive; }
    public LiveData<long[]>                getNetComparison()   { return netComparisonLive; }

    public void saveExpense(ExpenseEntity expense) {
        repository.insertExpense(expense, new AppRepository.DataCallback<Void>() {
            @Override
            public void onSuccess(Void data) {
                saveStatusLive.postValue("✅ Expense saved! " + System.currentTimeMillis());
            }
            @Override public void onError(String message) {
                saveStatusLive.postValue("❌ Could not save expense: " + message);
            }
        });
    }

    public void saveRevenue(RevenueEntity revenue) {
        repository.insertRevenue(revenue, new AppRepository.DataCallback<Void>() {
            @Override
            public void onSuccess(Void data) {
                saveStatusLive.postValue("✅ Income saved! " + System.currentTimeMillis());
            }
            @Override public void onError(String message) {
                saveStatusLive.postValue("❌ Could not save income: " + message);
            }
        });
    }

    public void loadTransactions(String month, String year) {
        repository.getExpensesByMonth(month, year, new AppRepository.DataCallback<List<ExpenseEntity>>() {
            @Override
            public void onSuccess(List<ExpenseEntity> expenses) {
                repository.getRevenuesByMonth(month, year, new AppRepository.DataCallback<List<RevenueEntity>>() {
                    @Override
                    public void onSuccess(List<RevenueEntity> revenues) {
                        List<TransactionItem> merged = new ArrayList<>();
                        long calculatedExpense = 0;
                        long calculatedIncome = 0;

                        for (ExpenseEntity e : expenses) {
                            merged.add(TransactionItem.fromExpense(e));
                            calculatedExpense += e.getAmount();
                        }
                        for (RevenueEntity r : revenues) {
                            merged.add(TransactionItem.fromRevenue(r));
                            calculatedIncome += r.getAmount();
                        }
                        Collections.sort(merged, (a, b) -> Long.compare(b.getDate(), a.getDate()));
                        transactionsLive.postValue(merged);

                        totalIncomeLive.postValue(calculatedIncome);
                        totalExpenseLive.postValue(calculatedExpense);
                        balanceLive.postValue(calculatedIncome - calculatedExpense);

                        calculateAllTimeStats();
                        comparePreviousMonth(month, year, calculatedIncome - calculatedExpense);
                    }
                    @Override public void onError(String msg) {}
                });
            }
            @Override public void onError(String msg) {}
        });
    }

    // Lifetime totals. The old version queried getByMonth("", ""), which never matches a row,
    // so the All-Time cards always showed 0.
    private void calculateAllTimeStats() {
        repository.getAllTimeTotals(new AppRepository.DataCallback<long[]>() {
            @Override
            public void onSuccess(long[] totals) {
                allTimeIncomeLive.postValue(totals[0]);
                allTimeExpenseLive.postValue(totals[1]);
            }
            @Override public void onError(String msg) {}
        });
    }

    private void comparePreviousMonth(String month, String year, long currentNet) {
        String[] prev;
        try {
            prev = MonthChange.previous(month, year);
        } catch (NumberFormatException e) {
            return;
        }
        repository.getTotalRevenueByMonth(prev[0], prev[1], new AppRepository.DataCallback<Long>() {
            @Override
            public void onSuccess(Long income) {
                repository.getTotalExpenseByMonth(prev[0], prev[1], new AppRepository.DataCallback<Long>() {
                    @Override
                    public void onSuccess(Long expense) {
                        netComparisonLive.postValue(new long[]{ currentNet, income - expense });
                    }
                    @Override public void onError(String msg) {}
                });
            }
            @Override public void onError(String msg) {}
        });
    }

    public void deleteTransaction(TransactionItem item, String month, String year) {
        if (item.isExpense()) {
            repository.deleteExpenseById(item.getId(), new AppRepository.DataCallback<Void>() {
                @Override
                public void onSuccess(Void data) {
                    loadTransactions(month, year);
                }
                @Override public void onError(String msg) {}
            });
        } else {
            repository.deleteRevenueById(item.getId(), new AppRepository.DataCallback<Void>() {
                @Override
                public void onSuccess(Void data) {
                    loadTransactions(month, year);
                }
                @Override public void onError(String msg) {}
            });
        }
    }

    public void loadNews(String apiKey) {
        newsLoadingLive.postValue(true);
        repository.fetchNews("finance", "en", 8, apiKey, new AppRepository.DataCallback<NewsResponse>() {
            @Override
            public void onSuccess(NewsResponse response) {
                newsLoadingLive.postValue(false);
                if (response != null && response.getArticles() != null) {
                    newsLive.postValue(new ArrayList<>(response.getArticles()));
                }
            }
            @Override public void onError(String message) {
                newsLoadingLive.postValue(false);
                // Without this the error observer never fired, so the refresh spinner kept spinning.
                newsErrorLive.postValue(message != null ? message : "Network error");
            }
        });
    }

    public void loadStatsData(String month, String year, AppRepository.DataCallback<long[]> callback) {
        repository.getTotalRevenueByMonth(month, year, new AppRepository.DataCallback<Long>() {
            @Override
            public void onSuccess(Long income) {
                repository.getTotalExpenseByMonth(month, year, new AppRepository.DataCallback<Long>() {
                    @Override
                    public void onSuccess(Long expense) {
                        callback.onSuccess(new long[]{ income, expense });
                    }
                    @Override public void onError(String msg) {}
                });
            }
            @Override public void onError(String msg) {}
        });
    }
}