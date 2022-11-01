package com.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.data.client.BlogHttpClient;

import java.util.Objects;

public class Author implements Parcelable {
    private String name;
    private String avatar;


    public String getName() {
        return name;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getAvatarURL() {
        return BlogHttpClient.BASE_URL + BlogHttpClient.PATH + getAvatar();
    }

    protected Author(Parcel in) {
        name = in.readString();
        avatar = in.readString();
    }

    public static final Creator<Author> CREATOR = new Creator<Author>() {
        @Override
        public Author createFromParcel(Parcel in) {
            return new Author(in);
        }

        @Override
        public Author[] newArray(int size) {
            return new Author[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(name, author.name) &&
                Objects.equals(avatar, author.avatar);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(avatar);
    }

    @Override
    public int describeContents() {
        return 0;
    }


}
