package com.example.androidcrimereportingsystem.lost;

public class Found {

    String iduid, idtime, idname, idno, scuid, sctime, scname, regno, bookuid, booktime, booktitle, subject, electuid, electime, electtype, model,
            docuid, doctime, doctype, docname, otheruid, othertime, othername, otherno;
    long expiretime;

    public Found() {
    }

    public Found(String iduid, long expiretime, String idtime, String idname, String idno, String scuid, String sctime, String scname, String regno, String bookuid, String booktime, String booktitle, String subject, String electuid, String electime, String electtype, String model, String docuid,
                 String doctime, String doctype, String docname, String otheruid, String othertime, String othername, String otherno) {
        this.iduid = iduid;
        this.idtime = idtime;
        this.idname = idname;
        this.idno = idno;

    }

    public long getExpiretime() {
        return expiretime;
    }

    public void setExpiretime(long expiretime) {
        this.expiretime = expiretime;
    }

    public String getIduid() {
        return iduid;
    }

    public void setIduid(String iduid) {
        this.iduid = iduid;
    }

    public String getIdtime() {
        return idtime;
    }

    public void setIdtime(String idtime) {
        this.idtime = idtime;
    }

    public String getIdname() {
        return idname;
    }

    public void setIdname(String idname) {
        this.idname = idname;
    }

    public String getIdno() {
        return idno;
    }

    public void setIdno(String idno) {
        this.idno = idno;
    }

}