package com.zxon.qqcontactdemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SimpleItemAnimator
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    val mContactGroups: MutableList<ContactGroup> = mutableListOf()

    lateinit var mRecyclerView: RecyclerView
    lateinit var mContactAdapter: ContactAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mRecyclerView = findViewById(R.id.recycler_view)
        mContactAdapter = ContactAdapter()
        mRecyclerView.adapter = mContactAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(this)

        if (mRecyclerView.itemAnimator is SimpleItemAnimator) {
            (mRecyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        }

        Thread {
            TimeUnit.SECONDS.sleep(1)
            val contactGroups = ContactStore.getContactGroupListSync()

            runOnUiThread { updateList(contactGroups) }

        }.start()
    }

    fun updateList(contactGroups: List<ContactGroup>) {
        mContactGroups.clear()
        mContactGroups.addAll(contactGroups)
        mContactAdapter.setData(mContactGroups)
        mContactAdapter.notifyDataSetChanged()
    }
}
