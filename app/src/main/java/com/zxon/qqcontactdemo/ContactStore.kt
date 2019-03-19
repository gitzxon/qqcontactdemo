package com.zxon.qqcontactdemo


object ContactStore {

    fun getContactGroupListSync(): List<ContactGroup> {
        val contactGroups = mutableListOf<ContactGroup>()

        (1..20).forEach {
            contactGroups.add(
                ContactGroup(
                    "group $it",
                    mutableListOf()
                )
            )
        }

        contactGroups.forEach {
            val num = (Math.random() * 5).toInt()
            for (i in 1..num) {
                it.contacts.add(Contact("联系人 $i", "${i}行白鹭上青天"))
            }
        }

        return contactGroups
    }
}