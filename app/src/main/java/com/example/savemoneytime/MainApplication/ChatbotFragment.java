package com.example.savemoneytime.MainApplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.savemoneytime.MainApplication.Adapters.ChatAdapter;
import com.example.savemoneytime.MainApplication.ViewModels.BudgetViewModel;
import com.example.savemoneytime.R;
import com.example.savemoneytime.model.ChatMessage;
import com.example.savemoneytime.model.ExpenseEntity;
import com.example.savemoneytime.model.RevenueEntity;
import com.example.savemoneytime.network.GeminiService;
import com.example.savemoneytime.util.AiReplyParser;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ChatbotFragment extends Fragment {

    private RecyclerView      rvChatMessages;
    private ChatAdapter       adapter;
    private List<ChatMessage> messageList;
    private EditText          edtChatInput;
    private FrameLayout       btnChatSend;
    private ImageView         btnChatCameraScan;

    private Button            chipBoba, chipLunch, chipUber, chipSalary;
    private BudgetViewModel   viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chatbot, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(BudgetViewModel.class);

        rvChatMessages    = view.findViewById(R.id.rv_chat_messages);
        edtChatInput      = view.findViewById(R.id.edt_chat_input);
        btnChatSend       = view.findViewById(R.id.btn_chat_send);
        btnChatCameraScan = view.findViewById(R.id.btn_chat_camera_scan);

        chipBoba          = view.findViewById(R.id.chip_boba);
        chipLunch         = view.findViewById(R.id.chip_lunch);
        chipUber          = view.findViewById(R.id.chip_uber);
        chipSalary        = view.findViewById(R.id.chip_salary);

        messageList = new ArrayList<>();

        messageList.add(new ChatMessage("Hi, I'm the BudgetMate assistant. Tell me what you spent or earned and I'll log it, for example \"Boba tea 6$\" or \"Salary 200$\".", ChatMessage.Sender.AI));

        adapter = new ChatAdapter(messageList);
        rvChatMessages.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvChatMessages.setAdapter(adapter);

        setupClickListeners();

        return view;
    }

    private void setupClickListeners() {
        btnChatSend.setOnClickListener(v -> {
            String text = edtChatInput.getText().toString().trim();
            if (!text.isEmpty()) {
                performChatAction(text);
                edtChatInput.setText("");
            }
        });

        btnChatCameraScan.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).openScannerFromChat();
            }
        });

        chipBoba.setOnClickListener(v -> performChatAction("Boba tea 6$"));
        chipLunch.setOnClickListener(v -> performChatAction("Lunch 15$"));
        chipUber.setOnClickListener(v -> performChatAction("Uber 20$"));
        chipSalary.setOnClickListener(v -> performChatAction("Salary 200$"));
    }

    private void performChatAction(String userInput) {
        addMessage(new ChatMessage(userInput, ChatMessage.Sender.USER));

        GeminiService.getInstance().parseTransaction(userInput, new GeminiService.GeminiCallback() {
            @Override
            public void onSuccess(String response) {
                runOnUi(() -> handleAiResponse(response));
            }

            @Override
            public void onError(String error) {
                runOnUi(() -> addMessage(new ChatMessage(error, ChatMessage.Sender.AI)));
            }
        });
    }

    private void handleAiResponse(String rawResponse) {
        AiReplyParser.Result r = AiReplyParser.parse(rawResponse);
        String reply;
        long now = Calendar.getInstance().getTimeInMillis();

        if (r.kind == AiReplyParser.Kind.EXPENSE) {
            viewModel.saveExpense(new ExpenseEntity(r.note, r.amount, r.category, now, "Logged by AI Chat"));
            reply = "Saved an expense of $" + r.amount + " in " + r.category + (r.note != null && !r.note.isEmpty() ? " (" + r.note + ")" : "") + ".";
        } else if (r.kind == AiReplyParser.Kind.REVENUE) {
            viewModel.saveRevenue(new RevenueEntity(r.note, r.amount, r.category, now, "Logged by AI Chat"));
            reply = "Saved income of $" + r.amount + " in " + r.category + (r.note != null && !r.note.isEmpty() ? " (" + r.note + ")" : "") + ".";
        } else {
            reply = r.message;
        }
        addMessage(new ChatMessage(reply, ChatMessage.Sender.AI));
    }

    private void addMessage(ChatMessage message) {
        if (adapter == null || rvChatMessages == null) return;
        messageList.add(message);
        adapter.notifyItemInserted(messageList.size() - 1);
        rvChatMessages.scrollToPosition(messageList.size() - 1);
    }

    /** Callbacks arrive on an OkHttp thread and may outlive the view; drop them if the view is gone. */
    private void runOnUi(Runnable action) {
        if (getActivity() == null) return;
        requireActivity().runOnUiThread(() -> {
            if (isAdded() && getView() != null) action.run();
        });
    }
}