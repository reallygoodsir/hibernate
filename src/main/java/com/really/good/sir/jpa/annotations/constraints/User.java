package com.really.good.sir.jpa.annotations.constraints;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "users")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // String validations
    @NotNull
    @NotBlank
    @NotEmpty
    @Size(min = 2, max = 20)
    @Pattern(regexp = "^[A-Za-z]+$", message = "Name must be letters only")
    private String name;

    @Email
    private String email;

    // Numeric validations
    @Min(1)
    @Max(120)
    private Integer age;

    @DecimalMin(value = "0.00", inclusive = true)
    @DecimalMax(value = "1000000.00", inclusive = true)
    private BigDecimal salary;

    @Positive
    private Integer positiveNumber;

    @PositiveOrZero
    private Integer positiveOrZero;

    @Negative
    private Integer negativeNumber;

    @NegativeOrZero
    private Integer negativeOrZero;

    @Digits(integer = 5, fraction = 2)
    private BigDecimal preciseNumber;

    // Date validations
    @Past
    private LocalDate birthDate;

    @PastOrPresent
    private LocalDate pastOrPresent;

    @Future
    private LocalDate futureDate;

    @FutureOrPresent
    private LocalDate futureOrPresent;

    // Boolean validations
    @AssertTrue
    private Boolean active;

    @AssertFalse
    private Boolean deleted;

    public User() {}

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Integer getPositiveNumber() {
        return positiveNumber;
    }

    public void setPositiveNumber(Integer positiveNumber) {
        this.positiveNumber = positiveNumber;
    }

    public Integer getPositiveOrZero() {
        return positiveOrZero;
    }

    public void setPositiveOrZero(Integer positiveOrZero) {
        this.positiveOrZero = positiveOrZero;
    }

    public Integer getNegativeNumber() {
        return negativeNumber;
    }

    public void setNegativeNumber(Integer negativeNumber) {
        this.negativeNumber = negativeNumber;
    }

    public Integer getNegativeOrZero() {
        return negativeOrZero;
    }

    public void setNegativeOrZero(Integer negativeOrZero) {
        this.negativeOrZero = negativeOrZero;
    }

    public BigDecimal getPreciseNumber() {
        return preciseNumber;
    }

    public void setPreciseNumber(BigDecimal preciseNumber) {
        this.preciseNumber = preciseNumber;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDate getPastOrPresent() {
        return pastOrPresent;
    }

    public void setPastOrPresent(LocalDate pastOrPresent) {
        this.pastOrPresent = pastOrPresent;
    }

    public LocalDate getFutureDate() {
        return futureDate;
    }

    public void setFutureDate(LocalDate futureDate) {
        this.futureDate = futureDate;
    }

    public LocalDate getFutureOrPresent() {
        return futureOrPresent;
    }

    public void setFutureOrPresent(LocalDate futureOrPresent) {
        this.futureOrPresent = futureOrPresent;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
