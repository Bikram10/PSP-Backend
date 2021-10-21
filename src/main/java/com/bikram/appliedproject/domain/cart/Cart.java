package com.bikram.appliedproject.domain.cart;

import com.bikram.appliedproject.domain.authentication.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private User user;

    @OneToMany(targetEntity = CartItem.class, orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id")
    @JsonIgnore
    private Set<CartItem> cartItems;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Set<CartItem> cartItems) {
        if(this.cartItems == null){
            this.cartItems = cartItems;
        }
        else{
            this.cartItems.retainAll(cartItems);
            this.cartItems.addAll(cartItems);
        }
    }

}
