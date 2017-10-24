package vn.phuongcong.fchat.data

/**
 * Created by vietcoscc on 10/20/2017.
 */
class Group(var groupKey: String, var adminKey: String, var groupName: String = "", var timeStamp: String = "") {
    constructor() : this("", "", "", "")
}