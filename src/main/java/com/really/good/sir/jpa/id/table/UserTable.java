package com.really.good.sir.jpa.id.table;

import javax.persistence.*;

@Entity
@Table(name = "users_table")
public class UserTable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "user_table_gen")
    @TableGenerator(
            name = "user_table_gen",
            table = "hibernate_sequences",
            pkColumnName = "seq_name",
            valueColumnName = "next_val",
            pkColumnValue = "USER_SEQ",
            allocationSize = 1
    )
    private Long id;

    private String name;
    private int age;

    public UserTable() {}
    public UserTable(String name, int age) { this.name = name; this.age = age; }

    public Long getId() { return id; }
}
