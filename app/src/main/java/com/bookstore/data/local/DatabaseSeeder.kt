package com.bookstore.data.local

import com.bookstore.data.local.dao.*
import com.bookstore.data.local.entity.*

object DatabaseSeeder {

    suspend fun seed(
        userDao: UserDao,
        bookDao: BookDao,
        addressDao: AddressDao,
        orderDao: OrderDao
    ) {
        if (bookDao.getBooksCount() > 0) return // Already seeded

        // === CATEGORIES ===
        val categories = listOf(
            CategoryEntity(id = 1, name = "Fiction", iconEmoji = "📖"),
            CategoryEntity(id = 2, name = "Non-Fiction", iconEmoji = "📰"),
            CategoryEntity(id = 3, name = "Science", iconEmoji = "🔬"),
            CategoryEntity(id = 4, name = "Technology", iconEmoji = "💻"),
            CategoryEntity(id = 5, name = "Self-Help", iconEmoji = "🌟")
        )
        bookDao.insertCategories(categories)

        // === BOOKS ===
        val books = listOf(
            // Fiction (categoryId=1)
            BookEntity(id = 1, title = "The Great Gatsby", author = "F. Scott Fitzgerald", description = "A classic American novel about the Jazz Age and the elusive American Dream set in the 1920s.", price = 299.0, originalPrice = 399.0, categoryId = 1, brand = "Penguin Classics", rating = 4.5f, ratingCount = 12450, deliveryDays = "3-5 days", isFeatured = true, isbn = "978-0743273565"),
            BookEntity(id = 2, title = "To Kill a Mockingbird", author = "Harper Lee", description = "A powerful story of racial injustice and moral growth in the American South.", price = 349.0, originalPrice = 449.0, categoryId = 1, brand = "Arrow Books", rating = 4.8f, ratingCount = 18900, deliveryDays = "2-4 days", isFeatured = true, isbn = "978-0061935466"),
            BookEntity(id = 3, title = "1984", author = "George Orwell", description = "A dystopian masterpiece exploring totalitarianism, surveillance and the power of truth.", price = 279.0, originalPrice = 350.0, categoryId = 1, brand = "Secker & Warburg", rating = 4.7f, ratingCount = 22100, deliveryDays = "3-5 days", isFeatured = true, isbn = "978-0451524935"),
            BookEntity(id = 4, title = "Pride and Prejudice", author = "Jane Austen", description = "The timeless story of the spirited Elizabeth Bennet and the proud Mr. Darcy.", price = 199.0, originalPrice = 299.0, categoryId = 1, brand = "Penguin Classics", rating = 4.6f, ratingCount = 15700, deliveryDays = "2-4 days", isbn = "978-0141439518"),
            BookEntity(id = 5, title = "The Alchemist", author = "Paulo Coelho", description = "A magical story about following your dreams and the journey to your personal legend.", price = 319.0, originalPrice = 399.0, categoryId = 1, brand = "HarperOne", rating = 4.4f, ratingCount = 31200, deliveryDays = "3-5 days", isFeatured = true, isbn = "978-0062315007"),

            // Non-Fiction (categoryId=2)
            BookEntity(id = 6, title = "Sapiens: A Brief History of Humankind", author = "Yuval Noah Harari", description = "A bold and wide-ranging look at the history of our species from ancient times to the present.", price = 449.0, originalPrice = 599.0, categoryId = 2, brand = "Harvill Secker", rating = 4.7f, ratingCount = 45600, deliveryDays = "2-4 days", isFeatured = true, isbn = "978-0062316097"),
            BookEntity(id = 7, title = "Educated", author = "Tara Westover", description = "A memoir about a young woman who grows up in a survivalist family and goes on to earn a PhD.", price = 399.0, originalPrice = 499.0, categoryId = 2, brand = "Random House", rating = 4.8f, ratingCount = 28900, deliveryDays = "3-5 days", isbn = "978-0399590504"),
            BookEntity(id = 8, title = "Becoming", author = "Michelle Obama", description = "A deeply personal memoir from the former First Lady of the United States.", price = 499.0, originalPrice = 699.0, categoryId = 2, brand = "Crown Publishing", rating = 4.9f, ratingCount = 52300, deliveryDays = "2-4 days", isFeatured = true, isbn = "978-1524763138"),
            BookEntity(id = 9, title = "The Body Keeps the Score", author = "Bessel van der Kolk", description = "How trauma reshapes body and brain, and innovative approaches to recovery.", price = 429.0, originalPrice = 549.0, categoryId = 2, brand = "Viking", rating = 4.8f, ratingCount = 19800, deliveryDays = "3-5 days", isbn = "978-0143127741"),
            BookEntity(id = 10, title = "Thinking, Fast and Slow", author = "Daniel Kahneman", description = "A tour of the mind and an explanation of the two systems that drive the way we think.", price = 379.0, originalPrice = 499.0, categoryId = 2, brand = "Farrar Straus", rating = 4.6f, ratingCount = 38700, deliveryDays = "2-4 days", isbn = "978-0374533557"),

            // Science (categoryId=3)
            BookEntity(id = 11, title = "A Brief History of Time", author = "Stephen Hawking", description = "From the Big Bang to black holes, Hawking explores the universe in a landmark book.", price = 349.0, originalPrice = 449.0, categoryId = 3, brand = "Bantam Books", rating = 4.7f, ratingCount = 41200, deliveryDays = "3-5 days", isFeatured = true, isbn = "978-0553380163"),
            BookEntity(id = 12, title = "The Gene: An Intimate History", author = "Siddhartha Mukherjee", description = "A fascinating narrative of the history, science and ethics of the gene.", price = 499.0, originalPrice = 649.0, categoryId = 3, brand = "Scribner", rating = 4.7f, ratingCount = 12300, deliveryDays = "2-4 days", isbn = "978-1476733524"),
            BookEntity(id = 13, title = "Cosmos", author = "Carl Sagan", description = "An exploration of our universe in space and time, our place in it, and who we are.", price = 429.0, originalPrice = 549.0, categoryId = 3, brand = "Random House", rating = 4.8f, ratingCount = 22100, deliveryDays = "3-5 days", isbn = "978-0345539434"),
            BookEntity(id = 14, title = "The Selfish Gene", author = "Richard Dawkins", description = "A landmark book on evolution that challenged conventional thinking about genes.", price = 319.0, originalPrice = 399.0, categoryId = 3, brand = "Oxford University Press", rating = 4.5f, ratingCount = 18900, deliveryDays = "2-4 days", isbn = "978-0198788607"),
            BookEntity(id = 15, title = "Surely You're Joking, Mr. Feynman!", author = "Richard Feynman", description = "Adventures of a curious character — Nobel laureate physicist Richard Feynman.", price = 299.0, originalPrice = 399.0, categoryId = 3, brand = "W. W. Norton", rating = 4.8f, ratingCount = 29400, deliveryDays = "3-5 days", isbn = "978-0393316049"),

            // Technology (categoryId=4)
            BookEntity(id = 16, title = "Clean Code", author = "Robert C. Martin", description = "A handbook of agile software craftsmanship with practical advice to write better code.", price = 649.0, originalPrice = 799.0, categoryId = 4, brand = "Prentice Hall", rating = 4.7f, ratingCount = 33400, deliveryDays = "2-4 days", isFeatured = true, isbn = "978-0132350884"),
            BookEntity(id = 17, title = "The Pragmatic Programmer", author = "David Thomas & Andrew Hunt", description = "From journeyman to master — practical advice to sharpen your edge as a programmer.", price = 699.0, originalPrice = 899.0, categoryId = 4, brand = "Addison-Wesley", rating = 4.8f, ratingCount = 28900, deliveryDays = "3-5 days", isbn = "978-0135957059"),
            BookEntity(id = 18, title = "Design Patterns", author = "Gang of Four", description = "Elements of reusable object-oriented software — a foundational programming text.", price = 749.0, originalPrice = 950.0, categoryId = 4, brand = "Addison-Wesley", rating = 4.5f, ratingCount = 19700, deliveryDays = "2-4 days", isbn = "978-0201633610"),
            BookEntity(id = 19, title = "You Don't Know JS", author = "Kyle Simpson", description = "A series diving deep into the core mechanisms of the JavaScript language.", price = 449.0, originalPrice = 599.0, categoryId = 4, brand = "O'Reilly Media", rating = 4.6f, ratingCount = 22100, deliveryDays = "3-5 days", isbn = "978-1491924464"),
            BookEntity(id = 20, title = "The Art of Computer Programming", author = "Donald Knuth", description = "The definitive mathematical treatment of algorithms and data structures.", price = 1999.0, originalPrice = 2499.0, categoryId = 4, brand = "Addison-Wesley", rating = 4.9f, ratingCount = 8700, deliveryDays = "4-6 days", isbn = "978-0201896831"),

            // Self-Help (categoryId=5)
            BookEntity(id = 21, title = "Atomic Habits", author = "James Clear", description = "An easy and proven way to build good habits and break bad ones using tiny changes.", price = 399.0, originalPrice = 499.0, categoryId = 5, brand = "Avery", rating = 4.9f, ratingCount = 61200, deliveryDays = "2-4 days", isFeatured = true, isbn = "978-0735211292"),
            BookEntity(id = 22, title = "The 7 Habits of Highly Effective People", author = "Stephen Covey", description = "A framework for personal effectiveness and principle-centered leadership.", price = 349.0, originalPrice = 449.0, categoryId = 5, brand = "Free Press", rating = 4.6f, ratingCount = 48900, deliveryDays = "3-5 days", isbn = "978-1982137274"),
            BookEntity(id = 23, title = "Deep Work", author = "Cal Newport", description = "Rules for focused success in a distracted world — how to cultivate deep focus.", price = 329.0, originalPrice = 429.0, categoryId = 5, brand = "Grand Central", rating = 4.7f, ratingCount = 34500, deliveryDays = "2-4 days", isbn = "978-1455586691"),
            BookEntity(id = 24, title = "Man's Search for Meaning", author = "Viktor E. Frankl", description = "A psychiatrist's memoir of surviving Nazi death camps and finding purpose in suffering.", price = 249.0, originalPrice = 349.0, categoryId = 5, brand = "Beacon Press", rating = 4.8f, ratingCount = 29100, deliveryDays = "3-5 days", isbn = "978-0807014271"),
            BookEntity(id = 25, title = "The Power of Now", author = "Eckhart Tolle", description = "A guide to spiritual enlightenment through present-moment awareness.", price = 299.0, originalPrice = 399.0, categoryId = 5, brand = "New World Library", rating = 4.5f, ratingCount = 39800, deliveryDays = "2-4 days", isbn = "978-1577314806")
        )
        bookDao.insertBooks(books)

        // === USERS ===
        val aliceId = userDao.insertUser(
            UserEntity(id = 1, name = "Alice Johnson", email = "alice@bookstore.com", passwordHash = hashPassword("password123"), giftPoints = 500)
        )
        val bobId = userDao.insertUser(
            UserEntity(id = 2, name = "Bob Smith", email = "bob@bookstore.com", passwordHash = hashPassword("password123"), giftPoints = 200)
        )

        // === ADDRESSES ===
        addressDao.insertAddress(AddressEntity(id = 1, userId = 1, label = "Home", fullAddress = "42 Elm Street, Bandra West", city = "Mumbai", pincode = "400050", isDefault = true))
        addressDao.insertAddress(AddressEntity(id = 2, userId = 1, label = "Office", fullAddress = "91 Tech Park, Whitefield", city = "Bengaluru", pincode = "560066", isDefault = false))
        addressDao.insertAddress(AddressEntity(id = 3, userId = 2, label = "Home", fullAddress = "7 MG Road, Connaught Place", city = "New Delhi", pincode = "110001", isDefault = true))
        addressDao.insertAddress(AddressEntity(id = 4, userId = 2, label = "Parents", fullAddress = "23 Anna Nagar East", city = "Chennai", pincode = "600102", isDefault = false))

        // === ORDERS ===
        val recentTime = System.currentTimeMillis() - (10 * 60 * 60 * 1000L) // 10 hours ago — cancellable
        val oldTime = System.currentTimeMillis() - (72 * 60 * 60 * 1000L)    // 72 hours ago — not cancellable

        val aliceOrder1Id = orderDao.insertOrder(OrderEntity(id = 1, userId = 1, totalAmount = 1097.0, discountAmount = 50.0, deliveryFee = 0.0, status = "CONFIRMED", placedAt = recentTime, deliveryAddress = "42 Elm Street, Bandra West, Mumbai - 400050"))
        orderDao.insertOrderItems(listOf(
            OrderItemEntity(orderId = 1, bookId = 1, quantity = 1, unitPrice = 299.0),
            OrderItemEntity(orderId = 1, bookId = 21, quantity = 1, unitPrice = 399.0),
            OrderItemEntity(orderId = 1, bookId = 16, quantity = 1, unitPrice = 649.0)
        ))

        val aliceOrder2Id = orderDao.insertOrder(OrderEntity(id = 2, userId = 1, totalAmount = 748.0, discountAmount = 0.0, deliveryFee = 49.0, status = "DELIVERED", placedAt = oldTime, deliveryAddress = "42 Elm Street, Bandra West, Mumbai - 400050"))
        orderDao.insertOrderItems(listOf(
            OrderItemEntity(orderId = 2, bookId = 11, quantity = 1, unitPrice = 349.0),
            OrderItemEntity(orderId = 2, bookId = 6, quantity = 1, unitPrice = 449.0)
        ))

        val bobOrder1Id = orderDao.insertOrder(OrderEntity(id = 3, userId = 2, totalAmount = 678.0, discountAmount = 20.0, deliveryFee = 0.0, status = "SHIPPED", placedAt = recentTime, deliveryAddress = "7 MG Road, Connaught Place, New Delhi - 110001"))
        orderDao.insertOrderItems(listOf(
            OrderItemEntity(orderId = 3, bookId = 3, quantity = 1, unitPrice = 279.0),
            OrderItemEntity(orderId = 3, bookId = 23, quantity = 1, unitPrice = 329.0),
            OrderItemEntity(orderId = 3, bookId = 24, quantity = 1, unitPrice = 249.0)
        ))

        val bobOrder2Id = orderDao.insertOrder(OrderEntity(id = 4, userId = 2, totalAmount = 349.0, discountAmount = 0.0, deliveryFee = 49.0, status = "DELIVERED", placedAt = oldTime, deliveryAddress = "7 MG Road, Connaught Place, New Delhi - 110001"))
        orderDao.insertOrderItems(listOf(
            OrderItemEntity(orderId = 4, bookId = 22, quantity = 1, unitPrice = 349.0)
        ))
    }

    fun hashPassword(password: String): String {
        // Simple deterministic hash for seeding — production should use BCrypt
        return password.hashCode().toString()
    }
}
