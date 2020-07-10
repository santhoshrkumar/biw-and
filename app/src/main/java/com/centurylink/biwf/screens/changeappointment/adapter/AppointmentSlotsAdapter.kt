package com.centurylink.biwf.screens.changeappointment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.centurylink.biwf.databinding.AppointmentSlotsItemsBinding

class AppointmentSlotsAdapter(
    var slotList: List<String>,
    private val slotSelectListener: SlotClickListener
) : RecyclerView.Adapter<AppointmentSlotsAdapter.SlotsViewHolder>() {

    var lastSelectedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlotsViewHolder {
        return SlotsViewHolder(AppointmentSlotsItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: SlotsViewHolder, position: Int) {
        val slotContent: String = slotList[position]
        holder.bind(slotContent, position)
    }

    override fun getItemCount(): Int = slotList.size

    inner class SlotsViewHolder(private var binding: AppointmentSlotsItemsBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(slotInfo: String, position: Int) {
            binding.appointmentTime.text = slotInfo
            binding.appointmentSelectRadioBtn.setOnClickListener {
                lastSelectedPosition = adapterPosition
                notifyDataSetChanged()
                slotSelectListener.onSlotSelected(slotList[lastSelectedPosition])
            }
            binding.appointmentSelectRadioBtn.isChecked = position == lastSelectedPosition
        }
    }

    interface SlotClickListener {

        /**
         * Handle click event on Item click
         *
         */
        fun onSlotSelected(slotInfo: String)

    }
}