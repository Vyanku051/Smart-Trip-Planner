package com.nashik.tripplanner.data;

import com.nashik.tripplanner.models.*;
import java.util.*;

/**
 * Central repository for Nashik-specific data including attractions, hotels, 
 * routes, and AI logic for the chatbot.
 */
public class NashikData {

    /**
     * Curated list of top tourist spots in Nashik.
     */
    public static List<Attraction> getAttractions() {
        List<Attraction> l = new ArrayList<>();
        l.add(new Attraction("a1", "Trimbakeshwar Temple", "Pilgrimage", "One of 12 sacred Jyotirlinga shrines. Godavari river originates here.", "Trimbak, Nashik 422212", "5:30AM–9:00PM", "Free", 4.9f, "2-3 hrs", "🙏", 19.9326, 73.5309));
        l.add(new Attraction("a2", "Sula Vineyards", "Wine Tour", "India's most celebrated winery. Award-winning wines.", "Govardhan, Nashik 422222", "11AM–11PM", "₹700", 4.7f, "3-4 hrs", "🍷", 20.0076, 73.7198));
        l.add(new Attraction("a3", "Pandavleni Caves", "Heritage", "24 Buddhist rock-cut caves from 1st century BC. Stunning carvings.", "Trirashmi Road, Nashik", "9AM–5:30PM", "₹15", 4.5f, "2 hrs", "🏛️", 19.9557, 73.8249));
        l.add(new Attraction("a4", "Panchavati & Ram Kund", "Pilgrimage", "Sacred Godavari ghats where Lord Ram stayed.", "Panchavati, Nashik 422003", "24 hrs", "Free", 4.6f, "2-3 hrs", "🙏", 20.0027, 73.7843));
        l.add(new Attraction("a5", "Anjneri Hill", "Trekking", "Birthplace of Lord Hanuman.", "Anjneri Village, Trimbak Road", "Sunrise–Sunset", "Free", 4.4f, "4-5 hrs", "🏔️", 20.0534, 73.5731));
        l.add(new Attraction("a6", "Dugarwadi Waterfall", "Nature", "Hidden waterfall in dense forest.", "Dugarwadi Village, Igatpuri", "Jun–Sep only", "₹50", 4.3f, "3-4 hrs", "🌊", 19.7003, 73.5600));
        l.add(new Attraction("a7", "Someshwar Waterfall", "Nature", "Popular family spot with ancient Shiva temple.", "Someshwar, Nashik", "6AM–6PM", "Free", 4.2f, "2 hrs", "🌊", 19.9721, 73.8567));
        l.add(new Attraction("a8", "Nashik Coin Museum", "Culture", "2500 years of Indian coins.", "Old Agra Road, Nashik", "10AM–5:30PM (Closed Mon)", "Free", 4.1f, "1-2 hrs", "🏺", 20.0004, 73.7906));
        l.add(new Attraction("a9", "Muktidham Temple", "Pilgrimage", "White marble temple.", "College Road, Nashik", "7AM–9PM", "Free", 4.6f, "1-2 hrs", "🙏", 19.9924, 73.7967));
        l.add(new Attraction("a10", "Kalaram Temple", "Pilgrimage", "Ancient black-stone Ram temple.", "Panchavati, Nashik", "5:30AM–9:30PM", "Free", 4.7f, "1 hr", "🙏", 20.0031, 73.7825));
        return l;
    }

    /**
     * Recommended hotels across budget ranges.
     */
    public static List<Hotel> getHotels() {
        List<Hotel> l = new ArrayList<>();
        l.add(createHotel("h1", "Beyond by Sula", 4, 4.7f, 8500, "Sula Vineyards", "Stay inside a working vineyard with stunning lake views.", "🍷", "https://images.unsplash.com/photo-1542314831-068cd1dbfeeb?auto=format&fit=crop&w=800&q=80", "Pool", "Wine Tasting", "Lake View"));
        l.add(createHotel("h2", "The Soulmate Hotel", 5, 4.8f, 4500, "Agra Highway", "Luxury hotel offering premium comfort and world-class service.", "🏨", "https://images.unsplash.com/photo-1566073771259-6a8506099945?auto=format&fit=crop&w=800&q=80", "Pool", "Spa", "Gym"));
        l.add(createHotel("h3", "Express Inn", 4, 4.5f, 3800, "Pathardi Phata", "Premium business hotel with elegant rooms and fine dining.", "💼", "https://images.unsplash.com/photo-1445019980597-93fa8acb246c?auto=format&fit=crop&w=800&q=80", "Pool", "Gym", "Bar"));
        l.add(createHotel("h4", "Ginger Nashik", 3, 4.3f, 2200, "Pathardi Phata", "Smart and affordable stay for business and leisure travelers.", "⚡", "https://images.unsplash.com/photo-1520250497591-112f2f40a3f4?auto=format&fit=crop&w=800&q=80", "WiFi", "AC", "Restaurant"));
        l.add(createHotel("h5", "The Gateway Hotel", 5, 4.6f, 6200, "Ambad", "Set in 20 acres of lush gardens, offering a tranquil escape.", "🌿", "https://images.unsplash.com/photo-1551882547-ff40c63fe5fa?auto=format&fit=crop&w=800&q=80", "Garden", "Pool", "Luxury"));
        l.add(createHotel("h6", "Radisson Blu Hotel", 5, 4.9f, 9500, "Pathardi Phata", "Contemporary luxury with exceptional views of the Pandavleni Caves.", "🏙️", "https://images.unsplash.com/photo-1564501049412-61c2a3083791?auto=format&fit=crop&w=800&q=80", "Spa", "Infinity Pool", "Fine Dine"));
        l.add(createHotel("h7", "Hotel Emerald Park", 3, 4.1f, 3200, "Sharanpur Road", "Centrally located with modern amenities and warm hospitality.", "🌳", "https://images.unsplash.com/photo-1496417263034-38ec4f0b665a?auto=format&fit=crop&w=800&q=80", "WiFi", "Parking", "Cafe"));
        l.add(createHotel("h8", "The Source at Sula", 4, 4.7f, 7800, "Sula Vineyards", "India's first heritage winery resort with Tuscan-style decor.", "🍇", "https://images.unsplash.com/photo-1502672260266-1c1ef2d93688?auto=format&fit=crop&w=800&q=80", "Vineyard View", "Pool", "Bicycles"));
        l.add(createHotel("h9", "Hotel Panchavati Elite Inn", 3, 4.0f, 2500, "Trimbak Road", "Value-for-money stay close to key city attractions.", "🏨", "https://images.unsplash.com/photo-1582719478250-c89cae4dc85b?auto=format&fit=crop&w=800&q=80", "AC", "Room Service", "WiFi"));
        l.add(createHotel("h14", "Lords Inn", 3, 4.2f, 2800, "Sharanpur Road", "Elegant rooms near Panchavati temples, ideal for pilgrims.", "🏰", "https://images.unsplash.com/photo-1571896349842-33c89424de2d?auto=format&fit=crop&w=800&q=80", "WiFi", "Restaurant", "Temple View"));
        return l;
    }

    private static Hotel createHotel(String id, String name, int stars, float rating, int price, String addr, String desc, String emoji, String imgUrl, String... amenities) {
        Hotel h = new Hotel(id, name, stars, rating, price, addr, 0, desc, emoji, Arrays.asList(amenities));
        h.setImageUrl(imgUrl);
        return h;
    }

    /**
     * Common travel routes in and around Nashik with traffic status.
     */
    public static List<Route> getRoutes() {
        List<Route> l = new ArrayList<>();
        l.add(createRoute("r1", "CBS", "Trimbakeshwar", "28 km", "45 min", "low", "₹40", "Bus", "Trimbak Road", 19.9975, 73.7898, 19.9405, 73.5358));
        l.add(createRoute("r2", "Nashik Road Station", "Panchavati", "9 km", "25 min", "heavy", "₹20", "Auto", "Dwarka Circle", 19.9646, 73.8188, 20.0113, 73.7901));
        l.add(createRoute("r3", "Mumbai Naka", "Sula Vineyards", "13 km", "30 min", "moderate", "₹250", "Cab", "Gangapur Road", 19.9861, 73.7801, 20.0076, 73.7198));
        return l;
    }

    private static Route createRoute(String id, String f, String t, String dst, String dur, String tr, String fr, String m, String v, double fLt, double fLn, double tLt, double tLn) {
        Route r = new Route(id, f, t, dst, dur, tr, fr, m, v);
        r.setFromLat(fLt); r.setFromLng(fLn); r.setToLat(tLt); r.setToLng(tLn);
        return r;
    }

    /**
     * Returns names of all attractions for autocomplete.
     */
    public static List<String> getSpotNames() {
        List<String> names = new ArrayList<>();
        for (Attraction a : getAttractions()) names.add(a.getName());
        return names;
    }

    /**
     * Returns suggested spots based on travel type (solo, couple, etc.)
     */
    public static List<String> getSpotsByType(String type) {
        if ("solo".equals(type)) return Arrays.asList("Harihar Fort", "Anjneri Hill", "Pandavleni Caves");
        if ("couple".equals(type)) return Arrays.asList("Sula Vineyards", "Someshwar Waterfall", "Gangapur Dam");
        if ("family".equals(type)) return Arrays.asList("Panchavati", "Muktidham Temple", "Coin Museum");
        return getSpotNames();
    }

    /**
     * Generates a Trip object based on multiple preferences.
     */
    public static Trip generateTrip(String name, String dest, String mood, String pace, String travelType, String sd, String ed, int budget, int travelers) {
        String finalDest = (dest == null || dest.isEmpty()) ? "Nashik, Maharashtra" : dest;
        String finalName = (name == null || name.isEmpty()) ? "Trip to " + finalDest : name;
        
        Trip t = new Trip(UUID.randomUUID().toString(), finalName, finalDest, sd, ed, travelType, mood, budget, travelers, getMoodEmoji(mood), getMoodColor(mood));
        t.setPace(pace);
        t.setNotes(getMoodNotes(mood));
        List<ItineraryDay> itinDays = buildItinerary(mood, sd, finalDest);
        t.setItineraryDays(itinDays);
        
        // Logic for budget: hide activities if mood suggests free spots or based on destinations
        boolean free = "pilgrimage".equals(mood) || "nature".equals(mood);
        int dayCount = itinDays.size();
        t.setBudgetBreakdown(new BudgetBreakdown(budget, dayCount, free));

        t.setPackingList(buildPackingList(mood));
        return t;
    }

    public static String getMoodName(String m) {
        switch (m != null ? m : "") {
            case "pilgrimage": return "Nashik Pilgrimage Tour";
            case "wine_tour": return "Sula Wine Escape";
            case "adventure": return "Nashik Adventure Trek";
            case "heritage": return "Nashik Heritage Tour";
            case "nature": return "Nashik Nature Retreat";
            case "family": return "Nashik Family Fun";
            default: return "Nashik Exploration";
        }
    }

    public static String getMoodEmoji(String m) {
        switch (m != null ? m : "") {
            case "pilgrimage": return "🙏";
            case "wine_tour": return "🍷";
            case "adventure": return "🏔️";
            case "heritage": return "🏛️";
            case "nature": return "🌿";
            case "family": return "👨‍👩‍👧‍👦";
            default: return "🌿";
        }
    }

    public static int getMoodIcon(String m) {
        switch (m != null ? m : "") {
            case "pilgrimage": return com.nashik.tripplanner.R.drawable.ic_pilgrimage;
            case "wine_tour": return com.nashik.tripplanner.R.drawable.ic_wine;
            case "adventure": return com.nashik.tripplanner.R.drawable.ic_adventure;
            case "heritage": return com.nashik.tripplanner.R.drawable.ic_heritage;
            case "nature": return com.nashik.tripplanner.R.drawable.ic_nature;
            case "family": return com.nashik.tripplanner.R.drawable.ic_family;
            default: return com.nashik.tripplanner.R.drawable.ic_home;
        }
    }

    public static String getMoodColor(String m) {
        switch (m != null ? m : "") {
            case "pilgrimage": return "#FF6B35";
            case "wine_tour": return "#8E44AD";
            case "adventure": return "#27AE60";
            default: return "#1E5C9B";
        }
    }

    static String getMoodNotes(String m) {
        switch (m != null ? m : "") {
            case "pilgrimage": return "🙏 Dress modestly at temples. Start early (5:30 AM).";
            case "wine_tour": return "🍷 Pre-book Sula tours. Enjoy the vineyard sunset.";
            default: return "🌿 Enjoy your Nashik trip!";
        }
    }

    static List<ItineraryDay> buildItinerary(String mood, String startDate, String dest) {
        List<ItineraryDay> days = new ArrayList<>();
        String dayTitle = (dest != null && !dest.isEmpty() && !dest.contains("Nashik")) ? dest + " & More" : "Nashik Highlights";
        ItineraryDay d1 = new ItineraryDay(1, startDate, dayTitle);
        
        if ("pilgrimage".equals(mood)) {
            d1.addActivity(new ItineraryItem("6:00 AM", "Visit " + (dest != null ? dest : "Ram Kund"), dest != null ? dest : "Panchavati", "activity", "🌊"));
            d1.addActivity(new ItineraryItem("9:00 AM", "Kalaram Temple", "Panchavati", "activity", "🙏"));
            d1.addActivity(new ItineraryItem("11:30 AM", "Trimbakeshwar Temple", "Nashik", "activity", "🙏"));
        } else if ("wine_tour".equals(mood)) {
            d1.addActivity(new ItineraryItem("11:00 AM", (dest != null && dest.contains("Sula")) ? "Wine Tasting & Tour" : "Sula Vineyards Tour", dest != null ? dest : "Sula Vineyards", "activity", "🍷"));
            d1.addActivity(new ItineraryItem("3:00 PM", "York Winery", "Gangapur", "activity", "🥂"));
        } else if ("adventure".equals(mood)) {
            d1.addActivity(new ItineraryItem("7:00 AM", (dest != null && !dest.isEmpty()) ? "Trek to " + dest : "Anjneri Hill Trek", dest != null ? dest : "Anjneri", "activity", "🏔️"));
            d1.addActivity(new ItineraryItem("1:00 PM", "Pandavleni Caves", "Nashik", "activity", "🏛️"));
        } else if ("heritage".equals(mood)) {
            d1.addActivity(new ItineraryItem("10:00 AM", dest != null ? dest : "Pandavleni Caves", dest != null ? dest : "Nashik", "activity", "🏛️"));
            d1.addActivity(new ItineraryItem("2:00 PM", "Nashik Coin Museum", "Old Nashik", "activity", "🏺"));
        } else {
            d1.addActivity(new ItineraryItem("10:00 AM", dest != null ? dest : "Pandavleni Caves", dest != null ? dest : "Nashik", "activity", "🏛️"));
            d1.addActivity(new ItineraryItem("3:00 PM", "Someshwar Waterfall", "Nashik", "activity", "🌊"));
        }
        days.add(d1);
        return days;
    }

    static PackingList buildPackingList(String mood) {
        PackingList l = new PackingList();
        l.addItem(new PackingItem("pk1", "ID Card", true));
        l.addItem(new PackingItem("pk2", "Charger", true));
        return l;
    }

    public static String getAIResponse(String input) {
        String m = input.toLowerCase().trim();
        
        if (has(m, "1 day", "one day", "1-day")) {
            return "🗓️ **Perfect 1-Day Nashik Itinerary:**\n\n" +
                   "• **Morning (6:00 AM - 9:00 AM):** Start your day with a spiritual visit to **Ram Kund** and the historic **Kalaram Temple** in Panchavati. Experience the serene morning Aarti.\n" +
                   "• **Late Morning (10:30 AM):** Head towards **Trimbakeshwar Shiva Temple** (approx. 30km from city). It's one of the 12 Jyotirlingas.\n" +
                   "• **Afternoon (1:30 PM):** Enjoy an authentic Maharashtrian Thali for lunch. We recommend 'Sadhana Misal' for a local taste.\n" +
                   "• **Evening (4:00 PM onwards):** Relax at **Sula Vineyards**. Take a winery tour, enjoy wine tasting, and watch a beautiful sunset over the backwaters.\n\n" +
                   "Hope this helps! Let me know if you need specific details about any spot. 🌿";
        }

        if (has(m, "2 day", "two days", "2-day")) {
            return "🗓️ **2-Day Nashik Exploration:**\n\n" +
                   "**Day 1: Spiritual & Heritage**\n" +
                   "• Visit Panchavati (Ram Kund, Kalaram Temple, Sita Gufa) and Trimbakeshwar in the afternoon.\n" +
                   "• Evening: Explore the local markets at Main Road.\n\n" +
                   "**Day 2: Vineyards & History**\n" +
                   "• Morning: Hike up to **Pandavleni Caves** for a panoramic view of the city.\n" +
                   "• Afternoon: Visit **Sula Vineyards** or **York Winery** for a relaxing tour and tasting session.\n" +
                   "• Evening: Visit **Gangapur Dam** backwaters for a peaceful sunset.";
        }

        if (has(m, "3 day", "three days", "3-day", "weekend")) {
            return "🗓️ **3-Day/Weekend Nashik Getaway:**\n\n" +
                   "**Day 1: The Spiritual Circuit**\n" +
                   "• Full day covering Ram Kund, Kalaram Temple, Muktidham, and Trimbakeshwar.\n\n" +
                   "**Day 2: Wine & Nature**\n" +
                   "• Morning: **Anjneri Hill** trek (Birthplace of Hanuman).\n" +
                   "• Afternoon: Wine tasting tour at **Sula Vineyards**.\n\n" +
                   "**Day 3: Adventure & Leisure**\n" +
                   "• Morning: **Dugarwadi Waterfall** (Seasonal) or **Someshwar Waterfall**.\n" +
                   "• Afternoon: Visit the **Coin Museum** and shopping at College Road.\n" +
                   "• Evening: Relaxing boat ride at MTDC Boat Club.";
        }

        if (has(m, "week", "7 days", "long trip")) {
            return "🗓️ **Complete 1-Week Nashik & Around:**\n\n" +
                   "• **Day 1-2:** Explore Nashik City (Temples, Pandavleni, Food tour).\n" +
                   "• **Day 3:** Trimbakeshwar and a trek to **Harihar Fort** (Iconic steep steps).\n" +
                   "• **Day 4:** Wine Valley tour - Visit Sula, Soma, and Vallonné Vineyards.\n" +
                   "• **Day 5:** Nature day at **Bhandardara Dam** (approx. 2.5 hours from Nashik).\n" +
                   "• **Day 6:** Visit **Shirdi Sai Baba Temple** (approx. 1.5 hours from Nashik).\n" +
                   "• **Day 7:** Leisure morning, last-minute shopping for Chivda and Paithani sarees, and departure.\n\n" +
                   "This plan covers spirituality, adventure, and relaxation! 🚗";
        }
        
        if (has(m, "misal", "food", "eat", "restaurant")) {
            return "🍛 **Nashik Food Guide - Must Try Delicacies:**\n\n" +
                   "1. **Authentic Misal Pav:** Nashik is world-famous for its spicy Misal. Top spots: **Sadhana Chulivarchi Misal**, **Mamacha Mala**, and **Vihar Misal**.\n" +
                   "2. **Maharashtrian Thali:** For a full meal, visit **Hotel Panchavati** or **Purohit Thali**.\n" +
                   "3. **Street Food:** Try the 'Sabudana Vada' near Ram Kund or the 'Chaat' at College Road.\n" +
                   "4. **Fine Dining:** For a premium experience, try **The Gateway Hotel** or restaurants at **Sula Vineyards**.\n\n" +
                   "Would you like me to find the nearest Misal spot for you? 🌶️";
        }

        if (has(m, "hotel", "stay", "accommodation")) {
            return "🏨 **Top Recommendations for your stay in Nashik:**\n\n" +
                   "• **For Luxury:** Radisson Blu or Beyond by Sula offer world-class amenities and great views.\n" +
                   "• **For Business:** Express Inn or The Gateway Hotel are centrally located with excellent facilities.\n" +
                   "• **For Budget:** Ginger Nashik or Hotel Panchavati provide clean, comfortable, and affordable rooms.\n" +
                   "• **For Pilgrims:** Lords Inn or hotels near Trimbak Road are very convenient.\n\n" +
                   "You can browse the **Hotels** tab in this app for more detailed pricing and to 'Book Now'! 🛌";
        }

        if (has(m, "temple", "spiritual", "pilgrimage")) {
            return "🙏 **Spiritual Journey in Nashik:**\n\n" +
                   "• **Trimbakeshwar:** A major Jyotirlinga located 30km from Nashik. Best visited early morning to avoid crowds.\n" +
                   "• **Panchavati:** The area where Lord Rama stayed. Must-visit spots include **Kalaram Temple**, **Sita Gufa**, and **Ram Kund**.\n" +
                   "• **Muktidham:** A beautiful temple made of white marble, representing all major Hindu deities.\n" +
                   "• **Someshwar:** One of the oldest temples dedicated to Lord Shiva, located on the banks of Godavari.\n\n" +
                   "Remember to dress modestly while visiting these sacred sites. 🌸";
        }

        if (has(m, "hello", "hi", "hey")) {
            return "Hello! 👋 I'm your **Nashik AI Travel Assistant**.\n\n" +
                   "I can help you plan your trip to the Wine Capital of India! Ask me about:\n" +
                   "• 🗓️ **Itineraries** (e.g., 'Plan a 2-day trip')\n" +
                   "• 🍛 **Local Food** (e.g., 'Where to eat Misal?')\n" +
                   "• 🏨 **Hotels** (e.g., 'Best hotels near Sula')\n" +
                   "• 🏛️ **Sightseeing** (e.g., 'Tell me about temples')\n\n" +
                   "How can I make your Nashik trip memorable today?";
        }

        return "I'm not sure I understand, but I'd love to help! 🌿\n\n" +
               "I specialize in Nashik travel tips. You can ask me about **hotels**, **best food**, **one-day plans**, or **spiritual spots** like Trimbakeshwar.\n\n" +
               "Try asking: *'Suggest some luxury hotels'* or *'Where can I get the best Misal?'*";
    }

    static boolean has(String text, String... keys) {
        for (String k : keys) if (text.contains(k)) return true;
        return false;
    }

    public static List<Trip> getDefaultTrips() {
        List<Trip> list = new ArrayList<>();
        list.add(new Trip("d1", "Nashik Pilgrimage", "Sacred Tour", "Oct 23", "Oct 25", "couple", "pilgrimage", 5000, 2, "🙏", "#FF6B35"));
        return list;
    }

    public static List<AppNotification> getDefaultNotifications() {
        List<AppNotification> list = new ArrayList<>();
        list.add(new AppNotification("n1", "alert", "SulaFest is coming in February!", "09:00 AM", true, false, "#8E44AD", "🍷"));
        return list;
    }

    public static List<OnboardSlide> getSlides() {
        List<OnboardSlide> list = new ArrayList<>();
        list.add(new OnboardSlide("🗺️", "Plan Your Nashik Trip", "AI-powered itineraries just for Nashik."));
        list.add(new OnboardSlide("🍷", "Explore Wineries", "Discover India's Wine Capital."));
        return list;
    }

    public static class OnboardSlide {
        public final String emoji, title, desc;
        public OnboardSlide(String e, String t, String d) { emoji = e; title = t; desc = d; }
    }
}
