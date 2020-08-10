package com.fp.cloud.utility;


import com.fp.cloud.configuration.oauth.Oauth2ResponseError;
import com.fp.cloud.main.global.payload.Response;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.mariadb.jdbc.internal.com.send.authentication.ed25519.Utils.bytesToHex;

public class Utility {
    public static String objectToString(Object o) {
        Gson g = new Gson();
        String json = g.toJson(o);
        return json;
    }

    public static Object jsonToObject(String json) {
        Gson g = new Gson();
        Object o = g.fromJson(json, Object.class);
        return o;
    }

    public static String getUser() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        if (authentication == null)
            return "";
        return authentication.getName();
    }

    public static ResponseEntity setResponse(HttpStatus httpStatus, String message, Object data) {
        if (httpStatus.equals(HttpStatus.OK)) {
            return setResponse(message, data);
        }
        String errorDesc = "";
        Oauth2ResponseError oauth2ResponseError = getClientMessage(message);
        if (oauth2ResponseError != null)
            errorDesc = oauth2ResponseError.getErrorDescription();
        Response resp = new Response();
        resp.setCode(httpStatus.value());
        resp.setStatus(httpStatus.getReasonPhrase());
        resp.setMessage(errorDesc);
        resp.setData(data);
        return new ResponseEntity<Response>(resp, httpStatus);
    }

    public static ResponseEntity setResponse(String message, Object data) {
        Response resp = new Response();
        resp.setCode(HttpStatus.OK.value());
        resp.setStatus(HttpStatus.OK.getReasonPhrase());
        resp.setMessage(message);
        resp.setData(data);
        return new ResponseEntity<Response>(resp, HttpStatus.OK);
    }

    public static Oauth2ResponseError getClientMessage(String message) {
        Oauth2ResponseError response = new Gson().fromJson(message, Oauth2ResponseError.class);
        return response;
    }

    public static boolean checkPasswordWithTimestamp(String passFromDb, String passRequest, String timeStamp) {
        boolean returnValue = false;
        try {
            MessageDigest mdSHA256 = MessageDigest.getInstance("SHA-256");
            byte[] baPasswdHashdb = mdSHA256.digest((timeStamp + passFromDb).getBytes(StandardCharsets.UTF_8));
            String strPasswdHashdb = bytesToHex(baPasswdHashdb).toLowerCase();

            if (passRequest.equals(strPasswdHashdb)) returnValue = true;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return returnValue;
    }

    public static Map toMap(Throwable map) {
        Map<String, String> newMap = new HashMap<>();
        if (map != null) {
            String[] aArr = (map.toString()).split(",");
            for (String strA : aArr) {
                String[] bArr = strA.split("=");
                for (String strB : bArr) {
                    newMap.put(bArr[0], strB.trim());
                }
            }
        } else {
            newMap.put("error", "");
            newMap.put("error_description", "");
        }
        return newMap;
    }

    public static String convertDate(String date, String from, String to) {
        Date dateFrom = parseDate(date, from);
        SimpleDateFormat sdf = null;
        try {
             sdf = new SimpleDateFormat(to);
             return sdf.format(dateFrom);
        } catch (Exception e){
            e.getMessage();
        }
        return null;
    }

    public static Date parseDate(String date, String format) {
        SimpleDateFormat sdf = null;
        try {
             sdf = new SimpleDateFormat(format);
             return sdf.parse(date);
        } catch (Exception e){
            e.getMessage();
        }
        return null;
    }

    public static String getDate(String format){
        DateTimeFormatter dtf;
        if (format.isEmpty())
            dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        else
            dtf = DateTimeFormatter.ofPattern(format);
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public static Date getDate(){
        String date = getDate("yyyy-MM-dd");
        return parseDate(date, "yyyy-MM-dd");
    }

    public static String getClientId(String base64){
        String str = new String(Base64.getDecoder().decode(base64));
        String[] arr = str.split(":");
        return arr[0];
    }
}
