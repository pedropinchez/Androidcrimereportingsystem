package com.example.androidcrimereportingsystem.lost;

public class Lost {

    public String nameid,idnumber,namesc,regno,booktitle,subject,placebook,uniquebook,electronictype,model,placeelectronic,uniqueelectronic,
            documenttype,namedocument,uniquedocument,item,itemdescription,number;


    public Lost(){}

    public Lost(String nameid, String idnumber, String namesc, String regno, String booktitle, String subject, String placebook, String uniquebook, String electronictype, String model, String placeelectronic,
                String uniqueelectronic, String documenttype, String number, String namedocument, String uniquedocument, String item, String itemdescription) {
        this.nameid = nameid;
        this.idnumber = idnumber;
        this.namesc = namesc;
        this.regno = regno;
        this.booktitle = booktitle;
        this.number = number;
        this.subject = subject;
        this.placebook = placebook;
        this.uniquebook = uniquebook;
        this.electronictype = electronictype;
        this.model = model;
        this.placeelectronic = placeelectronic;
        this.uniqueelectronic = uniqueelectronic;
        this.documenttype = documenttype;
        this.namedocument = namedocument;
        this.uniquedocument = uniquedocument;
        this.item = item;
        this.itemdescription = itemdescription;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNameid() {
        return nameid;
    }

    public void setNameid(String nameid) {
        this.nameid = nameid;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }

    public String getNamesc() {
        return namesc;
    }

    public void setNamesc(String namesc) {
        this.namesc = namesc;
    }

    public String getRegno() {
        return regno;
    }

    public void setRegno(String regno) {
        this.regno = regno;
    }

    public String getBooktitle() {
        return booktitle;
    }

    public void setBooktitle(String booktitle) {
        this.booktitle = booktitle;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPlacebook() {
        return placebook;
    }

    public void setPlacebook(String placebook) {
        this.placebook = placebook;
    }

    public String getUniquebook() {
        return uniquebook;
    }

    public void setUniquebook(String uniquebook) {
        this.uniquebook = uniquebook;
    }

    public String getElectronictype() {
        return electronictype;
    }

    public void setElectronictype(String electronictype) {
        this.electronictype = electronictype;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPlaceelectronic() {
        return placeelectronic;
    }

    public void setPlaceelectronic(String placeelectronic) {
        this.placeelectronic = placeelectronic;
    }

    public String getUniqueelectronic() {
        return uniqueelectronic;
    }

    public void setUniqueelectronic(String uniqueelectronic) {
        this.uniqueelectronic = uniqueelectronic;
    }

    public String getDocumenttype() {
        return documenttype;
    }

    public void setDocumenttype(String documenttype) {
        this.documenttype = documenttype;
    }

    public String getNamedocument() {
        return namedocument;
    }

    public void setNamedocument(String namedocument) {
        this.namedocument = namedocument;
    }

    public String getUniquedocument() {
        return uniquedocument;
    }

    public void setUniquedocument(String uniquedocument) {
        this.uniquedocument = uniquedocument;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getItemdescription() {
        return itemdescription;
    }

    public void setItemdescription(String itemdescription) {
        this.itemdescription = itemdescription;
    }
}
