package com.sparklinktech.stech.syncadaptertestdemo.syncadapter;

import com.sparklinktech.stech.syncadaptertestdemo.Product;

import org.json.JSONObject;

public class ProductParser
{ public static Product parse(JSONObject jsonArticle)
{
    Product particle = new Product();

    particle.setPname   (jsonArticle.optString("pname"));
    particle.setPid     (jsonArticle.optString("pid"));
    particle.setSid     (jsonArticle.optString("sid"));
    particle.setCat     (jsonArticle.optString("cat"));
    particle.setDscr    (jsonArticle.optString("dscr"));
    particle.setMrp     (jsonArticle.optString("mrp"));
    particle.setOfferpr (jsonArticle.optString("offerpr"));
    particle.setViews   (jsonArticle.optString("views"));
    particle.setImg     (jsonArticle.optString("img"));

    return particle;
}
}