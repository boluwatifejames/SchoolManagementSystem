package com.billsTech.PortalSystem01.util;

import com.billsTech.PortalSystem01.request.ComplaintRequest;

public class EmailUtil {
    public static String getEmailMessage(String firstName,String lastName , String host, String matricNumber){
        ComplaintRequest request = new ComplaintRequest();
        return "A Complaint has been Issued against Mr "+firstName+" "+lastName+" ,click the link below to view "+"\n\n"+getlink(host,matricNumber);
    }
    private static String getlink(String host ,String matricNumber){
        return host + "/fetchComplaints?matricNumber="+ matricNumber;
    }
}
