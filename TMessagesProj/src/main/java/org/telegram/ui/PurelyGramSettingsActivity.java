package org.telegram.ui;

import android.content.Context;
import android.view.View;
import org.telegram.messenger.R;
import org.telegram.messenger.SharedConfig;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Cells.TextCheckCell;
import org.telegram.ui.Components.RecyclerListView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.ViewGroup;

public class PurelyGramSettingsActivity extends BaseFragment {

    private RecyclerListView listView;
    private ListAdapter adapter;

    private int hideOnlineRow;
    private int antiDeleteRow;
    private int hideTypingRow;
    private int hideReadRow;
    private int ghostStoriesRow;
    private int rowCount;

    @Override
    public View createView(Context context) {
        actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        actionBar.setTitle("PurelyGram");
        actionBar.setActionBarMenuOnItemClick(new ActionBar.ActionBarMenuOnItemClick() {
            @Override
            public void onItemClick(int id) {
                if (id == -1) finishFragment();
            }
        });

        rowCount = 0;
        hideOnlineRow = rowCount++;
        antiDeleteRow = rowCount++;
        hideTypingRow = rowCount++;
        hideReadRow = rowCount++;
        ghostStoriesRow = rowCount++;

        listView = new RecyclerListView(context);
        listView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new ListAdapter(context);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((view, position) -> {
            if (view instanceof TextCheckCell) {
                TextCheckCell cell = (TextCheckCell) view;
                if (position == hideOnlineRow) {
                    SharedConfig.hideOnlineStatus = !SharedConfig.hideOnlineStatus;
                    cell.setChecked(SharedConfig.hideOnlineStatus);
                } else if (position == antiDeleteRow) {
                    SharedConfig.antiDeleteMessages = !SharedConfig.antiDeleteMessages;
                    cell.setChecked(SharedConfig.antiDeleteMessages);
                } else if (position == hideTypingRow) {
                    SharedConfig.hideTypingStatus = !SharedConfig.hideTypingStatus;
                    cell.setChecked(SharedConfig.hideTypingStatus);
                } else if (position == hideReadRow) {
                    SharedConfig.hideReadStatus = !SharedConfig.hideReadStatus;
                    cell.setChecked(SharedConfig.hideReadStatus);
                } else if (position == ghostStoriesRow) {
                    SharedConfig.ghostModeStories = !SharedConfig.ghostModeStories;
                    cell.setChecked(SharedConfig.ghostModeStories);
                }
                SharedConfig.savePurelyConfig();
            }
        });

        fragmentView = listView;
        return fragmentView;
    }

    private class ListAdapter extends RecyclerView.Adapter {
        private Context context;

        public ListAdapter(Context context) {
            this.context = context;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            TextCheckCell cell = new TextCheckCell(context);
            cell.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
            return new RecyclerListView.Holder(cell);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            TextCheckCell cell = (TextCheckCell) holder.itemView;
            if (position == hideOnlineRow) {
                cell.setTextAndCheck("Скрыть онлайн", "Никто не увидит когда вы в сети", SharedConfig.hideOnlineStatus, true);
            } else if (position == antiDeleteRow) {
                cell.setTextAndCheck("Антиудаление", "Сохранять удалённые сообщения", SharedConfig.antiDeleteMessages, true);
            } else if (position == hideTypingRow) {
                cell.setTextAndCheck("Скрыть печатает...", "Никто не увидит что вы пишете", SharedConfig.hideTypingStatus, true);
            } else if (position == hideReadRow) {
                cell.setTextAndCheck("Скрыть прочитано", "Никто не увидит что вы прочитали", SharedConfig.hideReadStatus, true);
            } else if (position == ghostStoriesRow) {
                cell.setTextAndCheck("Режим призрака", "Смотреть истории анонимно", SharedConfig.ghostModeStories, true);
            }
        }

        @Override
        public int getItemCount() {
            return rowCount;
        }
    }
}
