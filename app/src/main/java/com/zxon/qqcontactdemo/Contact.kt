package com.zxon.qqcontactdemo

open class BaseContact(val name: String)

class Contact(
    name: String,
    val summary: String
) : BaseContact(name)

class ContactGroup(
    name: String,
    var contacts: MutableList<Contact>
) : BaseContact(name)
