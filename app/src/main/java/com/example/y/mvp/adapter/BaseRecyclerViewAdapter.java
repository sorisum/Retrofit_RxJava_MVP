package com.example.y.mvp.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.y.mvp.R;

import java.util.LinkedList;
import java.util.List;

/**
 * by 12406 on 2016/6/21.
 */
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEAD = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOT = 2;
    protected Context context;
    private OnItemClickListener<T> mOnItemClickListener;
    private OnItemLongClickListener<T> mOnLongClickListener;
    private List<T> mDatas = new LinkedList<>();
    private boolean isHead = false;
    private boolean isFoot = false;
    private int headLayout;


    public BaseRecyclerViewAdapter(List<T> mDatas) {
        if (mDatas != null) {
            this.mDatas = mDatas;
        }
    }

    public interface OnItemClickListener<T> {
        void onItemClick(View view, int position, T info);
    }

    public interface OnItemLongClickListener<T> {
        void onLongClick(View view, int position, T info);
    }

    public void setOnItemClickListener(OnItemClickListener<T> listener) {
        this.mOnItemClickListener = listener;
    }

    public void setOnLongClickListener(OnItemLongClickListener<T> listener) {
        this.mOnLongClickListener = listener;
    }

    public void setHeadLayout(int headLayout) {
        this.headLayout = headLayout;
    }

    public void setFoot(boolean foot) {
        this.isFoot = foot;
    }

    public void setHead(boolean head) {
        this.isHead = head;
    }

    public void addAll(List<T> datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        mDatas.remove(position);
        this.notifyDataSetChanged();
    }

    public void removeAll() {
        if (mDatas.size() != 0) {
            mDatas.clear();
        }
        this.notifyDataSetChanged();
    }


    public List<T> getDatas() {
        return mDatas;
    }

    @Override
    public int getItemViewType(int position) {
        if (isHead && position == 0) {
            return TYPE_HEAD;
        } else if (isFoot && getItemCount() - 1 == position) {
            return TYPE_FOOT;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        int type;
        if (isHead && isFoot) {
            type = 2;
        } else if (isHead || isFoot) {
            type = 1;
        } else {
            type = 0;
        }
        return mDatas.size() + type;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        switch (viewType) {
            case TYPE_HEAD:
                View headView = layoutInflater.inflate(headLayout, parent, false);
                getHeadLayoutId(headView);
                return new BaseViewHolder(headView);
            case TYPE_ITEM:
                return new BaseViewHolder(layoutInflater.inflate(getItemLayoutId(), parent, false));
            default:
                return new BaseViewHolder(layoutInflater.inflate(R.layout.item_foot, parent, false));
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) != TYPE_ITEM) {
            return;
        }
        final int pos = getItemPosition(position);
        final T data = mDatas.get(pos);
        if (data == null) {
            return;
        }
        onBind((BaseViewHolder) holder, position, data);
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(v, pos, data);
                }
            });
        }
        if (mOnLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnLongClickListener.onLongClick(v, pos, data);
                    return true;
                }
            });
        }
    }

    protected void getHeadLayoutId(View headView) {
    }

    protected abstract int getItemLayoutId();

    protected abstract void onBind(BaseViewHolder holder, int position, T data);

    private int getItemPosition(int position) {
        if (isHead) {
            position -= 1;
        }
        return position;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (!(getItemViewType(position) == TYPE_ITEM)) {
                        return gridManager.getSpanCount();
                    } else {
                        return 1;
                    }
                }
            });
        }
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        if (layoutParams != null && layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams stagger = (StaggeredGridLayoutManager.LayoutParams) layoutParams;
            if (!(getItemViewType(holder.getLayoutPosition()) == TYPE_ITEM)) {
                stagger.setFullSpan(true);
            } else {
                stagger.setFullSpan(false);
            }
        }
    }


    public class BaseViewHolder extends RecyclerView.ViewHolder {
        private Context context;

        public BaseViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
        }

        public <T extends View> T getView(int id) {
            SparseArray<View> viewSparseArray = (SparseArray<View>) itemView.getTag();
            if (null == viewSparseArray) {
                viewSparseArray = new SparseArray<>();
                itemView.setTag(viewSparseArray);
            }
            View childView = viewSparseArray.get(id);
            if (null == childView) {
                childView = itemView.findViewById(id);
                viewSparseArray.put(id, childView);
            }
            return (T) childView;
        }

        public Context getContext() {
            return context;
        }

        public TextView getTextView(int id) {
            return getView(id);
        }

        public ImageView getImageView(int id) {
            return getView(id);
        }

        public void setTextView(int id, CharSequence charSequence) {
            getTextView(id).setText(charSequence);
        }

        public void setTextColor(int id, int color) {
            getTextView(id).setTextColor(color);
        }

    }
}
