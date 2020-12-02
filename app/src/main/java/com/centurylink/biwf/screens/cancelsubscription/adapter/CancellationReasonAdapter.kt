package com.centurylink.biwf.screens.cancelsubscription.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.centurylink.biwf.R

class CancellationReasonAdapter(
    internal var context: Context,
    private var cancellationList: Array<String>
) :
        BaseAdapter() {

    internal class ViewHolder {
        var cancellationItem: TextView? = null
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val mViewHolder: ViewHolder?
        var viewItem = convertView
        if (convertView == null) {
            mViewHolder = ViewHolder()

            viewItem = LayoutInflater.from(convertView?.context).inflate(R.layout.cancellation_reason_item, parent)
            mViewHolder.cancellationItem = viewItem.findViewById(R.id.cancellation_reason_item)
            viewItem.tag = mViewHolder
        } else {
            mViewHolder = viewItem?.tag as ViewHolder?
        }

        mViewHolder?.cancellationItem?.text = cancellationList[position]
        return viewItem
    }

    override fun getItem(position: Int): Any {
        return 0
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return cancellationList.size
    }
}
