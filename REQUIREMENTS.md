# 📋 ToursAndTravels — Software Requirement Specification (SRS)

**Project Title**: ToursAndTravels: An Offline-First Android Application for City-Wise Tourist Discovery, Route Navigation, and Tour Booking  
**Degree**: Third Year Bachelor of Technology (B.Tech) in Information Technology  
**Institution**: MIT Academy of Engineering, Alandi (D), Pune  
**Academic Year**: 2026–2027  
**Document Version**: 1.0.0  

---

## 1. System Requirements & Specifications

### 1.1 Hardware Requirements

#### Development Environment
| Component | Minimum Specification | Recommended Specification |
| :--- | :--- | :--- |
| **Processor** | Intel Core i5 / AMD Ryzen 5 (4 Cores, 2.5 GHz+) | Intel Core i7 / AMD Ryzen 7 (8 Cores, 3.2 GHz+) |
| **RAM** | 8 GB DDR4 | 16 GB DDR4/DDR5 |
| **Storage** | 10 GB free disk space (SSD) | 25 GB free disk space (NVMe SSD) |
| **Display** | 1280 x 800 resolution | 1920 x 1080 Full HD |
| **Peripherals** | Keyboard, mouse, USB cable for physical device debugging | USB-C connection with fast data transfer |

#### Target Device / Android Runtime
| Component | Minimum Specification | Recommended Specification |
| :--- | :--- | :--- |
| **Device Type** | Android Smartphone / Android Virtual Device (AVD) | Physical Android Smartphone |
| **OS Version** | Android 7.0 Nougat (API Level 24) | Android 13 to Android 15 (API Levels 33–35) |
| **RAM** | 2 GB | 4 GB or higher |
| **Storage** | 100 MB free internal storage | 250 MB free internal storage |
| **Sensors & Connectivity** | GPS / Fused Location Provider, Wi-Fi or Cellular Data (for Maps) | Dual-band Wi-Fi, 4G/5G, High-accuracy GPS |

---

### 1.2 Software Requirements & Toolchains

| Category | Tool / Software | Required Version | Purpose |
| :--- | :--- | :--- | :--- |
| **Operating System** | Windows 10/11, macOS (12+), or Ubuntu Linux (20.04+) | 64-bit OS | Development Host OS |
| **Integrated Development Environment (IDE)** | Android Studio | Ladybug (2024.2.1) or newer | Application code editor, emulator manager & profiler |
| **Java Development Kit** | OpenJDK / Eclipse Temurin | JDK 17 | Core language compilation (`sourceCompatibility 17`) |
| **Android SDK** | Android SDK Platform Tools | Build Tools 35.0.0, SDK Platform 35 | Compiling against Android 15 APIs |
| **Build Automation** | Gradle & Android Gradle Plugin | Gradle 9.1.0, AGP 9.0.1 | Task execution, asset processing, packaging APK |
| **Version Control** | Git | 2.30+ | Source code tracking and GitHub remote repository |
| **Local Persistence Engine** | SQLite | 3.x (Built into Android OS) | Structured data persistence on client device |

---

## 2. Functional Requirements (FR)

### Module 1: Authentication & Session Management
- **FR-01 (User Registration)**:
  - **Description**: The system shall allow a new traveler to register with Name, Email, Mobile Number, and Password.
  - **Inputs**: `name` (String), `email` (valid email format), `mobileno` (10-digit number), `password` (alphanumeric).
  - **Validations**: Prevent empty fields; reject duplicate email registrations.
  - **Outputs**: Success toast and redirection to Login screen; error message if email exists.

- **FR-02 (User & Admin Login)**:
  - **Description**: The system shall authenticate both Travelers and Administrators using stored credentials.
  - **Role Segregation**: Users with `usertype='user'` are navigated to `UserHomeActivity`; users with `usertype='admin'` are navigated to `AdminHomeActivity`.
  - **Session Cache**: Successful authentication saves active user ID and credentials in the local `Configuration` table.

- **FR-03 (Session Logout & Cleardown)**:
  - **Description**: Users can log out at any time. Logging out deletes the session from the `Configuration` table and returns the user to `SplashScreenActivity`.

- **FR-04 (User Profile Update)**:
  - **Description**: Authenticated travelers shall be able to view and modify their profile details (Name, Email, Mobile Number, Password) in `MyProfileActivity`.

---

### Module 2: City Tourism Discovery & Exploration
- **FR-05 (Destination Showcase Carousel)**:
  - **Description**: The home dashboard shall display an automatic image carousel featuring high-resolution hero banners of top Indian travel destinations (Mumbai, Jaipur, Goa, Udaipur, Agra).

- **FR-06 (One-Tap City Quick-Filter Chips)**:
  - **Description**: The home screen shall provide dedicated quick-access chips for popular travel hubs:
    - Mumbai
    - Jaipur
    - Goa
    - Pune
    - Udaipur
    - All (All Indian Cities)
  - **Behavior**: Tapping a city chip filters the destination catalog to show only places located in that city.

- **FR-07 (Live In-List Search)**:
  - **Description**: In `FindPackagesActivity`, the system shall implement a dynamic `TextWatcher` that filters attractions in real time as the user types, matching against destination name and city location.

- **FR-08 (Multi-Criteria Sorting & Wishlist Chip)**:
  - **Description**: Travelers can sort or filter the active destination list through one-touch chips:
    - **All Places**: Displays all available packages.
    - **Saved Wishlist**: Displays only places bookmarked by the user.
    - **Price: Low to High**: Ascending order of package cost.
    - **Price: High to Low**: Descending order of package cost.
    - **Top Rated**: Descending order of traveler review rating (e.g. 5.0, 4.9, 4.8).

---

### Module 3: Destination Details & Navigation
- **FR-09 (Comprehensive Attraction Details)**:
  - **Description**: Tapping any destination card opens `PackagesDetailsActivity` displaying:
    - Landmark hero photo
    - Destination title, city, category, and tour duration
    - Transparent pricing in Indian Rupees (₹)
    - Curated highlights and inclusions
    - Best time to visit recommendation
    - Rating score badge
- **FR-10 (Wishlist / Favorite Toggle)**:
  - **Description**: In `PackagesDetailsActivity`, travelers can tap the heart icon to add/remove the attraction from their personal wishlist (`favorites` table). State persists across restarts.

- **FR-11 (Route Mapping & Google Maps Directions)**:
  - **Description**:
    - The app embeds a Google Maps route webview showing the driving path to the destination coordinates.
    - An external action button creates an Android `Intent.ACTION_VIEW` passing latitude and longitude to open turn-by-turn driving navigation in the native Google Maps app.

---

### Module 4: Tour Booking & Itinerary Management
- **FR-12 (Tour Booking Submission)**:
  - **Description**: From `PackagesDetailsActivity`, the traveler clicks **Book Now** to open `AddBookingsActivity`, pre-filled with the package name and city. The user specifies traveler name, contact number, and address.
  - **Persistence**: Saved to `bookings` table in SQLite.
- **FR-13 (Booking History Management)**:
  - **Description**: Under `MyBookings`, travelers can inspect all their past and upcoming reservations.
  - **Cancellation**: Travelers can cancel any active booking, removing it from the database.

---

### Module 5: Administrator Control Center
- **FR-14 (Tour Package Creation)**:
  - **Description**: Administrators can add new tourist destinations specifying title, city, description, price, and geographical coordinates.
- **FR-15 (Interactive GPS Coordinate Picker)**:
  - **Description**: Administrators can launch `MarkerDraggingActivity` (powered by Mappls SDK) to drag and drop a pin on a map to retrieve precise latitude and longitude.
- **FR-16 (Tour Package Deletion)**:
  - **Description**: Administrators can delete existing packages. Associated favorites are cascaded cleanly.
- **FR-17 (Platform Oversight)**:
  - **Description**:
    - `AppUsersActivity`: Administrators can view all registered travelers on the platform.
    - `ViewAllBookings`: Administrators can inspect all tour bookings across all travelers.

---

## 3. Non-Functional Requirements (NFR)

### 3.1 NFR-01: Performance & Responsiveness
- **App Startup Time**: Cold startup to `SplashScreenActivity` in `< 1.2 seconds` on mid-range Android hardware.
- **Search Latency**: Real-time filtering in `FindPackagesActivity` responds within `< 50 ms` per keystroke.
- **Database Query Time**: SQLite queries execute within `< 15 ms` for standard lookups.
- **Smooth Animations**: 60 fps rendering during activity transitions and RecyclerView scrolling.

### 3.2 NFR-02: Offline Reliability & Robustness
- **Zero Network Dependency for Core Workflows**: Browsing, searching, filtering, sorting, bookmarking, booking, and profile operations function 100% offline.
- **Graceful Degradation**: When internet or GPS is unavailable, map views display a user-friendly fallback prompt without crashing the application.
- **Data Integrity**: SQLite transactions ensure ACID compliance; all database modifications prevent partial or corrupted writes.

### 3.3 NFR-03: Usability & Human Interface
- **Design Philosophy**: Material Design 3 guidelines featuring a warm, welcoming palette (Terracotta `#B23A22`, Amber `#FFC107`, Sand `#FBF8F5`, Charcoal `#212121`).
- **Typography & Hierarchy**: Clear visual contrast, bold titles, subtitle tags, and distinguishable rating chips.
- **Touch Targets**: Minimum interactive touch targets of `48 x 48 dp` for buttons, cards, and chips.
- **Empty States**: Friendly empty-state illustrations and text when search returns 0 results or wishlist/bookings are empty.

### 3.4 NFR-04: Security & Data Privacy
- **Client Storage Isolation**: Database file `toursandtravels` is stored in the app's internal private sandbox directory (`/data/data/com.example.toursandtravels/databases/`), protected by Android Linux user ID sandboxing.
- **Cleartext Traffic Policy**: `android:usesCleartextTraffic="true"` configured appropriately for local map loading while maintaining secure intent navigation.
- **Input Sanitization**: Database inputs are escaped via `SQLiteDatabase` parameterized queries and `ContentValues` to prevent SQL injection vulnerabilities.

### 3.5 NFR-05: Maintainability & Code Quality
- **Separation of Concerns**: UI activities (`user/` and `admin/`), adapters (`adapters/`), and data layer (`DBClass.java`) are strictly partitioned.
- **Modern Build Tooling**: Upgraded to Android Gradle Plugin 9.0.1, Gradle 9.1.0, and target SDK 35 with Java 17 compatibility.

---

## 4. Database Schema Specification (Data Dictionary)

### Table: `users`
| Column Name | Data Type | Constraints | Description |
| :--- | :--- | :--- | :--- |
| `id` | INTEGER | PRIMARY KEY AUTOINCREMENT | Unique user identifier |
| `name` | TEXT | NOT NULL | Full name of user |
| `email` | TEXT | NOT NULL, UNIQUE | User email address (login credential) |
| `mobileno` | TEXT | NOT NULL | Contact telephone number |
| `password` | TEXT | NOT NULL | User account password |
| `usertype` | TEXT | NOT NULL DEFAULT 'user' | Account role: `'admin'` or `'user'` |

### Table: `packages`
| Column Name | Data Type | Constraints | Description |
| :--- | :--- | :--- | :--- |
| `id` | INTEGER | PRIMARY KEY AUTOINCREMENT | Unique tour package identifier |
| `name` | TEXT | NOT NULL | Name of the tourist attraction / package |
| `location` | TEXT | NOT NULL | City location (e.g. Mumbai, Jaipur, Goa) |
| `description` | TEXT | NULLABLE | Detailed description of the attraction |
| `price` | TEXT | NOT NULL | Package price in INR (e.g. "1799") |
| `latitude` | TEXT | NOT NULL | GPS latitude coordinate (e.g. "26.9239") |
| `longitude` | TEXT | NOT NULL | GPS longitude coordinate (e.g. "75.8267") |
| `imageResourceId` | INTEGER | NOT NULL | Android drawable resource identifier |
| `rating` | TEXT | NOT NULL DEFAULT '4.8' | Average star rating out of 5.0 |
| `category` | TEXT | NOT NULL DEFAULT 'City Tour'| Attraction type (Heritage, Beach, Palace, etc.) |
| `duration` | TEXT | NOT NULL DEFAULT '1 Day' | Tour duration (e.g. "Half Day", "2 Days") |
| `bestTime` | TEXT | NOT NULL DEFAULT 'Oct – Mar'| Recommended visiting season |
| `highlights` | TEXT | NULLABLE | Comma-separated package inclusions & features |

### Table: `favorites`
| Column Name | Data Type | Constraints | Description |
| :--- | :--- | :--- | :--- |
| `id` | INTEGER | PRIMARY KEY AUTOINCREMENT | Unique favorite row identifier |
| `package_id` | TEXT | UNIQUE, NOT NULL | Foreign reference to `packages.id` |

### Table: `bookings`
| Column Name | Data Type | Constraints | Description |
| :--- | :--- | :--- | :--- |
| `id` | INTEGER | PRIMARY KEY AUTOINCREMENT | Unique booking reservation identifier |
| `fort` | TEXT | NOT NULL | Destination / Package title |
| `location` | TEXT | NOT NULL | City location of the tour |
| `username` | TEXT | NOT NULL | Name of traveler who created booking |
| `mobileno` | TEXT | NOT NULL | Contact telephone number for booking |
| `address` | TEXT | NOT NULL | Traveler pickup / billing residential address |

### Table: `Configuration`
| Column Name | Data Type | Constraints | Description |
| :--- | :--- | :--- | :--- |
| `CName` | VARCHAR | NULLABLE | Session parameter key (e.g. `'logged_in'`) |
| `CValue` | VARCHAR | NULLABLE | Session parameter value |

---

## 5. Verification & Acceptance Criteria

| ID | Test Scenario | Acceptance Criteria | Status |
| :--- | :--- | :--- | :--- |
| **TC-01** | First launch without network | App launches splash screen, seeds SQLite database, and presents Login screen without network timeout. | ✅ Passed |
| **TC-02** | Default User Login | Logging in with `user@gmail.com` / `123456` routes to `UserHomeActivity` with personalized greeting. | ✅ Passed |
| **TC-03** | Default Admin Login | Logging in with `admin@gmail.com` / `123456` routes to `AdminHomeActivity` with administrative dashboard. | ✅ Passed |
| **TC-04** | City Filter Chips | Tapping "Jaipur" chip navigates to places view filtered exclusively to Jaipur landmarks. | ✅ Passed |
| **TC-05** | Real-Time Search | Typing "Beach" filters catalog dynamically to display Goa Baga Beach package. | ✅ Passed |
| **TC-06** | Price & Rating Sorting | Selecting "Price: Low to High" arranges packages in ascending cost order (₹999 first). | ✅ Passed |
| **TC-07** | Wishlist Bookmarking | Tapping heart icon updates bookmark in SQLite and reflects immediately under "Saved" filter. | ✅ Passed |
| **TC-08** | Google Maps Directions | Tapping "View in Maps" initiates intent to Google Maps app with correct origin and destination coordinates. | ✅ Passed |
| **TC-09** | Tour Reservation | Submitting booking form stores entry in `bookings` table and renders immediately in `MyBookings`. | ✅ Passed |
| **TC-10** | Admin Add Package | Creating a package in `AddPackageActivity` appends record to SQLite and displays in traveler catalog. | ✅ Passed |
