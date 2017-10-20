package vn.phuongcong.fchat.data

/**
 * Created by vietcoscc on 10/20/2017.
 */
class Group {
    constructor(arrMember: ArrayList<String>?, groupName: String, timeStamp: String) {
        this.arrMember = arrMember!!
        this.groupName = groupName
        this.timeStamp = timeStamp
    }

    lateinit var arrMember: ArrayList<String>;
    lateinit var groupName: String;
    lateinit var timeStamp: String;
}