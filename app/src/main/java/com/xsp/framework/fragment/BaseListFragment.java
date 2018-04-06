package com.xsp.framework.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.xsp.framework.R;
import com.xsp.framework.modle.ListItem;
import com.xsp.framework.util.HandleType;
import com.xsp.library.fragment.BaseFragment;
import com.xsp.library.util.ui.DimenUtil;
import com.xsp.library.view.CustomDrawable;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用列表类界面
 */
public class BaseListFragment extends BaseFragment {
    private static final String TITLE = "table_name";
    private static final String DATA = "data_list";

    private List<ListItem> mItemList;

    public static BaseListFragment getInstance(String tabName, ArrayList<ListItem> data) {
        BaseListFragment fragment = new BaseListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, tabName);
        bundle.putParcelableArrayList(DATA, data);
        fragment.setArguments(bundle);
        return fragment;

    }

    public BaseListFragment() {

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.common_list_fragment, container, false);
        mItemList = new ArrayList<>();
        mItemList.addAll((List<ListItem>) getArguments().get(DATA));

        ListView listView = (ListView) view.findViewById(R.id.id_common_list_view);
        listView.setAdapter(new MyListAdapter(getActivity()));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListItem item = mItemList.get(position);
                Class<?> clz = item.getItemClick();
                if (clz == null) {
                    Toast.makeText(getActivity(), R.string.common_resolve_to_develop, Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(getActivity(), item.getItemClick());
                intent.putExtra(HandleType.HANDLE_TYPE_KEY, item.getItemExtra());
                startActivity(intent);
            }
        });
        return view;
    }

    public class MyListAdapter extends BaseAdapter {
        private Context mContext;

        private MyListAdapter(Context context) {
            mContext = context;
        }

        @Override
        public int getCount() {
            return mItemList.size();
        }

        @Override
        public Object getItem(int position) {
            return mItemList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.common_text_item, null);
                holder = new ViewHolder();
                holder.textView = (TextView) convertView.findViewById(R.id.id_common_text_view);
                holder.textView.setBackgroundDrawable(CustomDrawable.getDrawable(R.color.red, R.color.white, DimenUtil.dp2px(30)));
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.textView.setText(mItemList.get(position).getItemName());

            return convertView;
        }

        class ViewHolder {
            TextView textView;
        }
    }

}
