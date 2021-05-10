package com.upgrad.FoodOrderingApp.service.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * The AddressEntity class is mapped to table 'address' in database
 * All the columns are mapped to its respective attributes of the class
 */

@Entity
@Table(name = "address")
@NamedQueries(
        {
                @NamedQuery(name = "addressByUuid",
                        query = "select a from AddressEntity a where a.uuid = :addressUuid"),
        }
)
public class AddressEntity implements Serializable {

    public AddressEntity() {
    }

    public AddressEntity(@NotNull String uuid, String flatBuilNo, String locality, String city, String pincode, StateEntity state) {
        this.uuid = uuid;
        this.flatBuilNo = flatBuilNo;
        this.locality = locality;
        this.city = city;
        this.pincode = pincode;
        this.state = state;
    }

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "UUID")
    @Size(max = 200)
    @NotNull
    private String uuid;

    @Column(name = "FLAT_BUIL_NUMBER")
    @Size(max = 255)
    private String flatBuilNo;

    @Column(name = "LOCALITY")
    @Size(max = 255)
    private String locality;

    @Column(name = "CITY")
    @Size(max = 30)
    private String city;

    @Column(name = "PINCODE")
    @Size(max = 30)
    private String pincode;

    @ManyToOne
    @JoinColumn(name = "STATE_ID")
    private StateEntity state;

    @Column(name = "ACTIVE")
    private Integer active;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "customer_address",
            joinColumns = @JoinColumn(name = "ADDRESS_ID"),
            inverseJoinColumns = @JoinColumn(name = "CUSTOMER_ID"))
    private List<CustomerEntity> customers;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFlatBuilNo() {
        return flatBuilNo;
    }

    public void setFlatBuilNo(String flatBuildNum) {
        this.flatBuilNo = flatBuildNum;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public StateEntity getState() {
        return state;
    }

    public void setState(StateEntity state) {
        this.state = state;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public List<CustomerEntity> getCustomers() {
        return customers;
    }

    public void setCustomers(List<CustomerEntity> customers) {
        this.customers = customers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        AddressEntity that = (AddressEntity) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(uuid, that.uuid)
                .append(flatBuilNo, that.flatBuilNo)
                .append(locality, that.locality)
                .append(city, that.city)
                .append(pincode, that.pincode)
                .append(state, that.state)
                .append(active, that.active)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(uuid)
                .append(flatBuilNo)
                .append(locality)
                .append(city)
                .append(pincode)
                .append(state)
                .append(active)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("uuid", uuid)
                .append("flatBuildNum", flatBuilNo)
                .append("locality", locality)
                .append("city", city)
                .append("pincode", pincode)
                .append("state", state)
                .append("active", active)
                .toString();
    }
}
