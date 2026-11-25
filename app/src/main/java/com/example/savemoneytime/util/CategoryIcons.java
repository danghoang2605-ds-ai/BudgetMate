package com.example.savemoneytime.util;

import androidx.annotation.DrawableRes;
import com.example.savemoneytime.R;
import java.util.Locale;

/** One Material Symbols (rounded) icon per category, replacing the emoji the app used before. */
public final class CategoryIcons {

    private CategoryIcons() {}

    @DrawableRes
    public static int forCategory(String category) {
        String c = category == null ? "" : category.toLowerCase(Locale.US);
        if (has(c, "food", "lunch", "dinner", "eat", "coffee", "boba", "pho")) return R.drawable.ms_restaurant;
        if (has(c, "transport", "uber", "grab", "taxi", "bus", "car", "gas")) return R.drawable.ms_directions_car;
        if (has(c, "rental")) return R.drawable.ms_apartment;
        if (has(c, "housing", "house", "rent", "home")) return R.drawable.ms_home;
        if (has(c, "health", "medic", "pharmacy", "gym", "doctor")) return R.drawable.ms_medication;
        if (has(c, "shop", "buy", "cloth")) return R.drawable.ms_shopping_bag;
        if (has(c, "entertain", "movie", "game", "concert")) return R.drawable.ms_sports_esports;
        if (has(c, "educat", "school", "book", "course")) return R.drawable.ms_school;
        if (has(c, "travel", "flight", "hotel", "trip")) return R.drawable.ms_flight;
        if (has(c, "utilit", "electric", "water", "internet")) return R.drawable.ms_bolt;
        if (has(c, "phone", "mobile")) return R.drawable.ms_smartphone;
        if (has(c, "gift", "bonus")) return R.drawable.ms_redeem;
        if (has(c, "salary", "wage", "paycheck")) return R.drawable.ms_payments;
        if (has(c, "business", "freelance", "client")) return R.drawable.ms_work;
        if (has(c, "invest", "dividend", "stock")) return R.drawable.ms_trending_up;
        if (has(c, "saving")) return R.drawable.ms_savings;
        if (has(c, "allowance", "others")) return R.drawable.ms_account_balance_wallet;
        if (has(c, "refund")) return R.drawable.ms_undo;
        if (has(c, "bill")) return R.drawable.ms_receipt_long;
        if (has(c, "other")) return R.drawable.ms_add;
        return R.drawable.ms_receipt_long;
    }

    private static boolean has(String text, String... words) {
        for (String w : words) if (text.contains(w)) return true;
        return false;
    }
}
