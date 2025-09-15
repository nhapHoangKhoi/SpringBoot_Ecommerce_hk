package com.hoangkhoi.springboot_ecommerce.model;

import com.hoangkhoi.springboot_ecommerce.enums.ProductStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "products")
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductImage> productImages;

    @Column(name = "description", length = 2000)
    private String description;

    @Column(name = "price", precision = 15, nullable = false)
    private BigDecimal price;

    @Column(name = "status", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductStatus status = ProductStatus.ACTIVE;

    @Column(name = "stock", nullable = false)
    private int stock = 0;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    @Column(name = "is_featured", nullable = false)
    private boolean isFeatured = false;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<CartItem> cartItems;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
