package com.liweidong.mymvp.model.bean;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Author:李炜东
 * Date:2018/9/11.
 * WX:17337162963
 * Email:18037891572@163.com
 */

/*
默认情况下，Room会为实体中定义的每个字段创建一列。
如果实体具有您不想保留的字段，则可以使用它们进行注释@Ignore。
您必须通过类中的entities 数组 引用实体 Database类。
 */

/*
每个实体必须至少定义一个字段作为主键。即使只有一个字段，您仍需要使用注释注释该字段 @PrimaryKey 。
另外，如果你想室自动分配ID的实体，您可以设置@PrimaryKey的 autoGenerate 属性。
如果实体具有复合主键，则可以使用注释的 primaryKeys 属性 @Entity

@Entity(primaryKeys = {"phone", "name"})
 */

/*
根据您访问数据的方式，您可能希望索引数据库中的某些字段以加快查询速度。
要向实体添加索引，请indices 在@Entity注释中包含该 属性 ，列出要包含在索引或复合索引中的列的名称。

@Entity(indices = {@Index("name"),
        @Index(value = {"last_name", "address"})})
 */

/*
有时，数据库中的某些字段或字段组必须是唯一的。您可以通过将 注释的unique 属性设置为来强制实施此唯一性属性 。以下代码示例可防止表具有两行，这些行包含与 列相同的值集：@IndextruefirstNamelastName

@Entity(indices = {@Index(value = {"first_name", "last_name"},
        unique = true)})
 */


/*
默认情况下，Room使用类名作为数据库表名。如果希望表具有不同的名称，请设置注释的 tableName 属性 @Entity

警告： SQLite中的表名称不区分大小写。
 */

/*
Serializable序列化对象可以持久存储(可选择入数据库、或文件的形式保存)

Parcelable序列化对象后可以用intent传递对象
bundle.putParcelable("book", book);

Book book = bun.getParcelable("book");


首先Parcelable的性能要强于Serializable的原因我需要简单的阐述一下
  1）. 在内存的使用中,前者在性能方面要强于后者
  2）. 后者在序列化操作的时候会产生大量的临时变量,(原因是使用了反射机制)从而导致GC的频繁调用,因此在性能上会稍微逊色
  3）. Parcelable是以Ibinder作为信息载体的.在内存上的开销比较小,因此在内存之间进行数据传递的时候,Android推荐使用Parcelable,
  既然是内存方面比价有优势,那么自然就要优先选择.
  4）. 在读写数据的时候,Parcelable是在内存中直接进行读写,而Serializable是通过使用IO流的形式将数据读写入在硬盘上.
  但是：虽然Parcelable的性能要强于Serializable,但是仍然有特殊的情况需要使用Serializable,
  而不去使用Parcelable,因为Parcelable无法将数据进行持久化,因此在将数据保存在磁盘的时候,仍然需要使用后者,
  因为前者无法很好的将数据进行持久化.(原因是在不同的Android版本当中,Parcelable可能会不同,因此数据的持久化方面仍然是使用Serializable
 */

@Entity(tableName = "PHONE")
public class PhoneBean implements Parcelable {

    @PrimaryKey(autoGenerate = true) // 设置主键
    @ColumnInfo(name = "ID") // 定义对应的数据库的字段名成
    private int id;

    @ColumnInfo(name = "PHONE")
    private String phone;

    @ColumnInfo(name = "NAME")
    private String name;

    @ColumnInfo(name = "DATE")
    private Date date;

/*    @Ignore
    Bitmap picture;*/

    public PhoneBean(String phone, String name, Date date) {
        this.phone = phone;
        this.name = name;
        this.date = date;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    //内容接口描述，默认返回0就可以
    public int describeContents() {
        return 0;
    }

    //将你的对象序列化为一个Parcel对象，即：将类的数据写入外部提供的Parcel中，打包需要传递的数据到Parcel容器保存，以便从 Parcel容器获取数据
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(phone);
        dest.writeString(name);
        dest.writeLong(this.date != null ? this.date.getTime() : -1);
    }

    /**
     * 这里的读的顺序必须与writeToParcel(Parcel dest, int flags)方法中
     * 写的顺序一致，否则数据会有差错，比如你的读取顺序如果是：
     * nickname = source.readString();
     * username=source.readString();
     * age = source.readInt();
     * 即调换了username和nickname的读取顺序，那么你会发现你拿到的username是nickname的数据，
     * 而你拿到的nickname是username的数据
     */
    protected PhoneBean(Parcel in) {
        id = in.readInt();
        phone = in.readString();
        name = in.readString();
        long tmpDate = in.readLong();
        this.date = tmpDate == -1 ? null : new Date(tmpDate);
    }

    public static final Creator<PhoneBean> CREATOR = new Creator<PhoneBean>() {
        /**
         * 供外部类反序列化本类数组使用
         */
        public PhoneBean createFromParcel(Parcel in) {
            return new PhoneBean(in);
        }

        /**
         * 从Parcel中读取数据
         */
        @Override
        public PhoneBean[] newArray(int size) {
            return new PhoneBean[size];
        }
    };


}
