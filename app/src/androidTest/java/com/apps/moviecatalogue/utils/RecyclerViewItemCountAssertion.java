package com.apps.moviecatalogue.utils;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;

import net.bytebuddy.matcher.ElementMatcher;

import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static junit.framework.TestCase.assertNotNull;
import static net.bytebuddy.matcher.ElementMatchers.is;

public class RecyclerViewItemCountAssertion implements ViewAssertion {
    private final int expectedCount;

    public RecyclerViewItemCountAssertion(int expectedCount) {
        this.expectedCount = expectedCount;
    }

    @Override
    public void check(View view, NoMatchingViewException noViewFoundException) {
        if (noViewFoundException != null) {
            throw noViewFoundException;
        }

        RecyclerView recyclerView = (RecyclerView) view;
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        assertNotNull(adapter);
        assertThat(adapter.getItemCount(), is(expectedCount));
    }

    private void assertThat(int itemCount, ElementMatcher.Junction<Object> objectJunction) {
    }
}