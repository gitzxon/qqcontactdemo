package com.zxon.qqcontactdemo

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.ViewGroup
import java.lang.IllegalStateException
import java.util.*
import kotlin.collections.ArrayList


class ContactAdapter : RecyclerView.Adapter<BaseContactViewHolder>() {

    companion object {
        const val TYPE_CONTACT = 1
        const val TYPE_CONTACT_GROUP = 2
    }

    val mContactGroups: MutableList<ContactGroup> = ArrayList()
    val mOpenedContactGroup: MutableSet<ContactGroup> = HashSet()

    override fun getItemCount(): Int {
        var count = 0
        mContactGroups.forEach {
            count += 1
            if (mOpenedContactGroup.contains(it)) {
                count += it.contacts.size
            }
        }
        Log.d("quick_log", "getItemCount : $count")
        return count
    }

    fun getItem(position: Int): BaseContact {

        var remaining = position
        var curGroupIndex = 0
        while (remaining >= 0) {
            val curGroup: ContactGroup = mContactGroups[curGroupIndex]

            if (remaining == 0) {
                return curGroup
            }

            remaining -= 1

            if (mOpenedContactGroup.contains(curGroup)) {
                if (remaining < curGroup.contacts.size) {
                    return curGroup.contacts[remaining]
                } else {
                    remaining -= curGroup.contacts.size
                    curGroupIndex += 1
                }
            } else {
                curGroupIndex += 1
            }
        }

        throw IllegalStateException("can not find the BaseContact item")
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position) is ContactGroup) {
            TYPE_CONTACT_GROUP
        } else {
            TYPE_CONTACT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseContactViewHolder {
        return if (viewType == TYPE_CONTACT) {
            ContactViewHolder(parent!!)
        } else {
            ContactGroupViewHolder(parent!!)
        }
    }

    override fun onBindViewHolder(holder: BaseContactViewHolder, position: Int) {
        val baseContact = getItem(position)
        if (holder is ContactGroupViewHolder) {
            holder.bind(this, baseContact as ContactGroup)
        } else if (holder is ContactViewHolder) {
            holder.bind(this, baseContact as Contact)
        }
    }



    fun toggleContactGroup(contactGroup: ContactGroup, adapterPosition: Int) {
        if (mOpenedContactGroup.contains(contactGroup)) {
            mOpenedContactGroup.remove(contactGroup)
            notifyItemRangeRemoved(adapterPosition + 1, contactGroup.contacts.size)
        } else {
            mOpenedContactGroup.add(contactGroup)
            notifyItemRangeInserted(adapterPosition + 1, contactGroup.contacts.size)
        }

    }

    fun setData(contactGroup: List<ContactGroup>) {
        mContactGroups.addAll(contactGroup)
        notifyDataSetChanged()
    }

}
