// Ka Chun Wan 101389677
// Yuk Fai Hsu 101395298
//Group 11

package com.example.project_group11.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.DocumentId;

import java.util.ArrayList;
import java.util.HashMap;

public class Problem implements Parcelable {
    @DocumentId
    private String documentID;
    private String userEmail;
    private String userNickname;
    private String userMobile;
    private String title;
    private String details;
    private double remuneration;
    private String date;
    private String address;
    private HashMap<String, Double> coordinates;
    private String solverEmail;
    private String solverName;
    private String reviewFromCustomer;

    public Problem() {}

    public Problem(String userEmail, String userNickname, String userMobile, String title, String details,
                   double remuneration, String date, String address, HashMap<String, Double> coordinates,
                   String solverEmail, String solverName, String reviewFromCustomer) {
        this.userEmail = userEmail;
        this.userNickname = userNickname;
        this.userMobile = userMobile;
        this.title = title;
        this.details = details;
        this.remuneration = remuneration;
        this.date = date;
        this.address = address;
        this.coordinates = coordinates;
        this.solverEmail = solverEmail;
        this.solverName = solverName;
        this.reviewFromCustomer = reviewFromCustomer;
    }

    protected Problem(Parcel in) {
        documentID = in.readString();
        userEmail = in.readString();
        userNickname = in.readString();
        userMobile = in.readString();
        title = in.readString();
        details = in.readString();
        remuneration = in.readDouble();
        date = in.readString();
        address = in.readString();
        solverEmail = in.readString();
        solverName = in.readString();
        reviewFromCustomer = in.readString();
        coordinates = in.readHashMap(HashMap.class.getClassLoader());
    }

    public static final Creator<Problem> CREATOR = new Creator<Problem>() {
        @Override
        public Problem createFromParcel(Parcel in) {
            return new Problem(in);
        }

        @Override
        public Problem[] newArray(int size) {
            return new Problem[size];
        }
    };

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public String getTitle() {
        return title;
    }

    public String getDetails() {
        return details;
    }

    public double getRemuneration() {
        return remuneration;
    }

    public String getDate() {
        return date;
    }

    public String getAddress() {
        return address;
    }

    public HashMap<String, Double> getCoordinates() {
        return coordinates;
    }

    public String getSolverEmail() {
        return solverEmail;
    }

    public String getSolverName() {
        return solverName;
    }

    public String getReviewFromCustomer() {
        return reviewFromCustomer;
    }

    public String getDocumentID() {
        return documentID;
    }

    public void setReviewFromCustomer(String reviewFromCustomer) {
        this.reviewFromCustomer = reviewFromCustomer;
    }

    @Override
    public String toString() {
        return "Problem{" +
                "documentID=" + documentID + '\'' +
                "userEmail='" + userEmail + '\'' +
                ", userNickname='" + userNickname + '\'' +
                ", userMobile='" + userMobile + '\'' +
                ", title='" + title + '\'' +
                ", details='" + details + '\'' +
                ", remuneration=" + remuneration +
                ", date='" + date + '\'' +
                ", address='" + address + '\'' +
                ", coordinates=" + coordinates +
                ", solverEmail='" + solverEmail + '\'' +
                ", solverName='" + solverName + '\'' +
                ", reviewFromCustomer='" + reviewFromCustomer + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(documentID);
        parcel.writeString(userEmail);
        parcel.writeString(userNickname);
        parcel.writeString(userMobile);
        parcel.writeString(title);
        parcel.writeString(details);
        parcel.writeDouble(remuneration);
        parcel.writeString(address);
        parcel.writeString(solverName);
        parcel.writeString(reviewFromCustomer);
        parcel.writeString(date);
        parcel.writeSerializable(coordinates);
        parcel.writeString(reviewFromCustomer);
    }
}
