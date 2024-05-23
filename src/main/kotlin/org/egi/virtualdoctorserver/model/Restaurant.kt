package org.egi.virtualdoctorserver.model

import jakarta.persistence.*

@Entity
@Table(name = "restaurant")
data class Restaurant(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(unique = true)
    val name: String,

    val streetAddress: String,
    val city: String,
    val postcode: String,
    val country: String,
    val telephone: String,

    @Column(unique = true)
    val email: String,
    @Column(unique = true)
    val website: String,

    @JoinColumn(name = "owner_id", nullable = false)
    @ManyToOne
    val owner: RestaurantOwner,

    @ManyToMany
    @JoinTable(
        name = "restaurant_menu",
        joinColumns = [JoinColumn(name = "restaurant_id")],
        inverseJoinColumns = [JoinColumn(name = "item_id")]
    )
    private val menu: MutableList<Item> = mutableListOf()
) {
    companion object {
        private val owners = RestaurantOwner.VALUES
        val VALUES = listOf(
            Restaurant(
                name = "Athens Delight",
                streetAddress = "123 Plaka St",
                city = "Athens",
                postcode = "10556",
                country = "Greece",
                telephone = "+30 21 0123 4567",
                email = "info@athensdelight.gr",
                website = "http://www.athensdelight.gr",
                owner = owners[0]
            ),
            Restaurant(
                name = "Santorini Sunset",
                streetAddress = "456 Fira St",
                city = "Santorini",
                postcode = "84700",
                country = "Greece",
                telephone = "+30 22 0123 4567",
                email = "info@santorinisunset.gr",
                website = "http://www.santorinisunset.gr",
                owner = owners[1]
            ),
            Restaurant(
                name = "Crete Cuisine",
                streetAddress = "789 Heraklion Ave",
                city = "Heraklion",
                postcode = "71202",
                country = "Greece",
                telephone = "+30 28 0123 4567",
                email = "info@cretecuisine.gr",
                website = "http://www.cretecuisine.gr",
                owner = owners[2]
            ),
            Restaurant(
                name = "Rhodes Retreat",
                streetAddress = "101 Lindos Rd",
                city = "Rhodes",
                postcode = "85107",
                country = "Greece",
                telephone = "+30 24 0123 4567",
                email = "info@rhodesretreat.gr",
                website = "http://www.rhodesretreat.gr",
                owner = owners[2]
            ),
            Restaurant(
                name = "Mykonos Magic",
                streetAddress = "202 Paradise Beach",
                city = "Mykonos",
                postcode = "84600",
                country = "Greece",
                telephone = "+30 22 0123 4567",
                email = "info@mykonosmagic.gr",
                website = "http://www.mykonosmagic.gr",
                owner = owners[2]
            ),
            Restaurant(
                name = "Corfu Charm",
                streetAddress = "303 Corfu Town",
                city = "Corfu",
                postcode = "49100",
                country = "Greece",
                telephone = "+30 26 0123 4567",
                email = "info@corfucharm.gr",
                website = "http://www.corfucharm.gr",
                owner = owners[0]
            ),
            Restaurant(
                name = "Thessaloniki Treats",
                streetAddress = "404 Aristotelous Square",
                city = "Thessaloniki",
                postcode = "54624",
                country = "Greece",
                telephone = "+30 23 0123 4567",
                email = "info@thessalonikitreats.gr",
                website = "http://www.thessalonikitreats.gr",
                owner = owners[1]
            ),
            Restaurant(
                name = "Nafplio Nosh",
                streetAddress = "505 Syntagma Square",
                city = "Nafplio",
                postcode = "21100",
                country = "Greece",
                telephone = "+30 27 0123 4567",
                email = "info@nafplionosh.gr",
                website = "http://www.nafplionosh.gr",
                owner = owners[2]
            ),
            Restaurant(
                name = "Patras Plates",
                streetAddress = "606 Patras Port",
                city = "Patras",
                postcode = "26222",
                country = "Greece",
                telephone = "+30 26 1023 4567",
                email = "info@patrasplates.gr",
                website = "http://www.patrasplates.gr",
                owner = owners[2]
            ),
            Restaurant(
                name = "Kavala Kitchen",
                streetAddress = "707 Kavala Bay",
                city = "Kavala",
                postcode = "65403",
                country = "Greece",
                telephone = "+30 25 0123 4567",
                email = "info@kavalakitchen.gr",
                website = "http://www.kavalakitchen.gr",
                owner = owners[2]
            )

        )
    }

    fun addToMenu(item: Item) = menu.add(item)
    fun deleteFromMenu(item: Item) = menu.remove(item)
    fun getMenuSize() = menu.size
}