
package com.sparklinktech.stech.syncadaptertestdemo;

import com.sparklinktech.stech.syncadaptertestdemo.ConnectToServer.ConnectToServer;

public class NewProduct
{


    private String _0=null;

    private String pid=null;

    private String _1=null;

    private String sid=null;

    private String _2=null;

    private String cat=null;

    private String _3=null;

    private String pname=null;

    private String _4=null;

    private String dscr=null;

    private String _5=null;

    private String mrp=null;

    private String _6=null;

    private String offerpr=null;

    private String _7=null;

    private String qty=null;

    private String _8=null;

    private String img=null;

    private String _9=null;

    private String views=null;

    public NewProduct(String Pid,String Pname,String Sid,String Cat,String Dscr,String Mrp,String Offerpr,String Views,String Img)
    {

        super();
        this.pid = Pid;
        this.pname = Pname;
        this.sid = Sid;
        this.cat = Cat;
        this.dscr = Dscr;
        this.mrp = Mrp;
        this.offerpr = Offerpr;
        this.views = Views;
        this.img = Img;



    }
    public String get0() {
        return _0;
    }

    public void set0(String _0) {
        this._0 = _0;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String get1() {
        return _1;
    }

    public void set1(String _1) {
        this._1 = _1;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String get2() {
        return _2;
    }

    public void set2(String _2) {
        this._2 = _2;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String get3() {
        return _3;
    }

    public void set3(String _3) {
        this._3 = _3;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String get4() {
        return _4;
    }

    public void set4(String _4) {
        this._4 = _4;
    }

    public String getDscr() {
        return dscr;
    }

    public void setDscr(String dscr) {
        this.dscr = dscr;
    }

    public String get5() {
        return _5;
    }

    public void set5(String _5) {
        this._5 = _5;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public String get6() {
        return _6;
    }

    public void set6(String _6) {
        this._6 = _6;
    }

    public String getOfferpr() {
        return offerpr;
    }

    public void setOfferpr(String offerpr) {
        this.offerpr = offerpr;
    }

    public String get7() {
        return _7;
    }

    public void set7(String _7) {
        this._7 = _7;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String get8() {
        return _8;
    }

    public void set8(String _8) {
        this._8 = _8;
    }

    public String getImg()
    {
        String str = ConnectToServer.URL_GETIMAGES;
        return str+img;

    }

    public void setImg(String img) {
        this.img = img;
    }

    public String get9() {
        return _9;
    }

    public void set9(String _9) {
        this._9 = _9;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    @Override
    public String toString() {

        return  pid + " " + pname+ " " + sid+ " " + cat+ " " + dscr+ " " + mrp+ " " + offerpr+ " " + views+ " " + img;

    }

}
