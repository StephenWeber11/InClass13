package com.uncc.mobileappdev.inclass13;

import android.view.View;

/**
 * Created by Stephen on 4/24/2018.
 */

public interface RecyclerViewClickListener {
    void recyclerViewListClicked(View v, int position);
    void removeItem(View v, int position);
}
