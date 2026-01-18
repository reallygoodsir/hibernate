package com.really.good.sir.jpa.annotations.constraints;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

public class AnnotationsConstraintsMain {
    public static void main(String[] args) {

        EntityManagerFactory emf = Configuration.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        // Create validator
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        User user = new User();
        user.setName("A"); // invalid: too short
        user.setEmail("not-an-email"); // invalid
        user.setAge(150); // invalid
        user.setSalary(new BigDecimal("-5")); // invalid
        user.setPositiveNumber(-1); // invalid
        user.setNegativeNumber(5); // invalid
        user.setPreciseNumber(new BigDecimal("123456.789")); // invalid
        user.setBirthDate(LocalDate.now().plusDays(1)); // invalid
        user.setFutureDate(LocalDate.now().minusDays(1)); // invalid
        user.setActive(false); // invalid
        user.setDeleted(true); // invalid

        // Validate
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        for (ConstraintViolation<User> v : violations) {
            System.out.println(v.getPropertyPath() + " -> " + v.getMessage());
        }

        em.close();
        emf.close();
    }
}
