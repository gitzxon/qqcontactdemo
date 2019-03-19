package com.zxon.qqcontactdemo

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


abstract class BaseContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun isGroup(): Boolean

    lateinit var mTitleView: TextView

    init {
        mTitleView = itemView.findViewById(R.id.title)
    }

}

class ContactGroupViewHolder(itemView: View) : BaseContactViewHolder(itemView) {

    constructor(viewGroup: ViewGroup) : this(
        LayoutInflater.from(viewGroup.context).inflate(
            R.layout.item_contact_group,
            viewGroup,
            false
        )
    )

    override fun isGroup(): Boolean = true

    fun bind(contactAdapter: ContactAdapter, contactGroup: ContactGroup) {
        mTitleView.text = contactGroup.name
        itemView.setOnClickListener {
            Log.d("quick_log", "contact group clicked ")
            contactAdapter.toggleContactGroup(contactGroup, adapterPosition)
        }

    }
}

class ContactViewHolder(itemView: View) : BaseContactViewHolder(itemView) {
    constructor(viewGroup: ViewGroup) : this(
        LayoutInflater.from(viewGroup.context).inflate(
            R.layout.item_contact,
            viewGroup,
            false
        )
    )

    override fun isGroup(): Boolean = false

    fun bind(contactAdapter: ContactAdapter, contact: Contact) {
        mTitleView.text = contact.name
    }
}