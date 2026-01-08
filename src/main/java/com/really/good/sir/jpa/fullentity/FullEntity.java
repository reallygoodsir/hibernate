package com.really.good.sir.jpa.fullentity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;

@Entity
@Table(name = "full_entity")
public class FullEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer age;
    private int score;

    @Column(name = "big_number")
    private Long bigNumber;

    @Column(name = "long_number")
    private long longNumber;

    private Double salary;
    private double rating;
    private Boolean active;
    private boolean verified;

    @Column(precision = 19, scale = 2)
    private BigDecimal balance;

    @Temporal(TemporalType.DATE)
    @Column(name = "birth_date")
    private Date birthDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "local_date")
    private LocalDate localDate;

    @Column(name = "local_date_time")
    private LocalDateTime localDateTime;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Lob
    private String description;

    @Lob
    private byte[] document;

    @Transient
    private String tempData;

    @Embedded
    private Address address;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public enum Status { NEW, IN_PROGRESS, DONE }

    @Override
    public String toString() {
        return "FullEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", score=" + score +
                ", bigNumber=" + bigNumber +
                ", longNumber=" + longNumber +
                ", salary=" + salary +
                ", rating=" + rating +
                ", active=" + active +
                ", verified=" + verified +
                ", balance=" + balance +
                ", birthDate=" + birthDate +
                ", createdAt=" + createdAt +
                ", localDate=" + localDate +
                ", localDateTime=" + localDateTime +
                ", status=" + status +
                ", description='" + description + '\'' +
                ", document=" + Arrays.toString(document) +
                ", tempData='" + tempData + '\'' +
                ", address=" + address +
                ", category=" + category +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Long getBigNumber() {
        return bigNumber;
    }

    public void setBigNumber(Long bigNumber) {
        this.bigNumber = bigNumber;
    }

    public long getLongNumber() {
        return longNumber;
    }

    public void setLongNumber(long longNumber) {
        this.longNumber = longNumber;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getDocument() {
        return document;
    }

    public void setDocument(byte[] document) {
        this.document = document;
    }

    public String getTempData() {
        return tempData;
    }

    public void setTempData(String tempData) {
        this.tempData = tempData;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
