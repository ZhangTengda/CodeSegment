package com.xnjz.one.room

class Readme {

//    Room 包含三个主要组件：
//
//    数据实体 Entity：用于表示应用数据库中的表
//    数据访问对象 Dao：提供可用于查询、更新、插入和删除数据库中的数据的方法
//    数据库类 Database：用于保存数据库并作为应用持久性数据底层连接的主要访问点

//    https://juejin.cn/post/7283748394601742376?searchId=2024020517335837762632F9C84C9E81A3


    /**
     *
    创建实体类
    首先我们来修改我们的实体类 Person，我们之前的 Person 只是个简单的数据类，还未与 Room 挂钩，现在我们设置注解@Entity，这样就告诉 Room 我们这个数据类是数据库的实体类。同时还可以在里面设置一些参数，比较常用的是tableName，就是指定你的表名称为什么，不然表名默认为实体类的名称。
    kotlin复制代码@Parcelize
    @Entity(tableName = "personTable")
    data class Person(
    val id: Long,
    val name: String,
    val telephone: String,
    ) : Parcelable

    定义主键
    最简单最基础的主键定义是在对应字段前面添加注解@PrimaryKey。
    其中可选添加参数autoGenerate，其值默认为 false，如果设置为 true 且主键类型为 Long 或 Int，每次插入数据会自动从1开始自增。
    kotlin复制代码@PrimaryKey(autoGenerate = true) val id: Long

    如果想要定义复合主键，这时候回到我们的@Entity注解，里面有个参数可以用于设置我们的复合主键
    kotlin复制代码@Entity(primaryKeys = ["name", "telephone"])

    忽略字段
    默认情况下，Room 会为实体中定义的每个字段创建一个列。 如果某个实体中有不想保留的字段，则可以使用 @Ignore 为这些字段添加注解
    kotlin复制代码@Parcelize
    @Entity(tableName = "personTable")
    data class Person(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String,
    val telephone: String,
    @Ignore val nickname: String
    ) : Parcelable

    如果实体继承了父实体的字段，使用 @Entity 注解的 ignoredColumns 属性通常会更容易些
    kotlin复制代码open class Friend {
    val nickname: String? = null
    }

    @Parcelize
    @Entity(tableName = "personTable", ignoredColumns = ["nickname"])
    data class Person(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String,
    val telephone: String,
    ) : Parcelable, Friend()

    修改数据类
    经过上面的介绍，我们可以开始修改我们的数据类。因为后续的数据插入逻辑是在固定数据源中随机选取，然后再添加至数据库。但由于之前的数据 id 值被写死，如果直接随机选取插入会造成主键重复，所以我们需要将主键的autoGenerate属性设为true，这样就不需要为 id 赋值，可以将其设为可空属性。
    在这里出现了一个新的注解@ColumnInfo，在实体类中，每个字段通常代表表中的一列，而此注解则用于自定义与字段所关联的列属性。其中比较常用的属性为name，用于指定字段所属列的名称为什么，不然列名默认为字段名称。
    kotlin复制代码@Parcelize
    @Entity(tableName = "personTable")
    data class Person(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "telephone") val telephone: String,
    ) : Parcelable

    修改数据源
    由于我们的数据类发生了一定的变动，原本写死的数据需要进行修改，将所有写定的 id 更改为 null 值。同时我们使用mutableListOf便于后续插入数据可以从list中随机选择。
    kotlin复制代码val list: MutableList<Person> = ArrayList(
    mutableListOf(
    Person(null, "Mike", "86100100"),
    Person(null, "Jane", "86100101"),
    Person(null, "John", "86100102"),
    Person(null, "Amy", "86100103"),
    )
    )

    作者：wowguo
    链接：https://juejin.cn/post/7283748394601742376
    来源：稀土掘金
    著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     */
}