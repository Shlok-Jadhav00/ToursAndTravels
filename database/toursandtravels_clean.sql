-- ========================================================
-- ToursAndTravels Database Schema & Clean Data Dump
-- Compatible with MySQL 5.7+, MySQL 8.0+, MariaDB 10.3+
-- Database: toursandtravels
-- ========================================================

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

CREATE DATABASE IF NOT EXISTS `toursandtravels` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `toursandtravels`;

-- --------------------------------------------------------
-- Table structure for table `users`
-- --------------------------------------------------------

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(150) NOT NULL,
  `email` varchar(150) NOT NULL UNIQUE,
  `mobileno` varchar(20) NOT NULL,
  `password` varchar(100) NOT NULL,
  `usertype` varchar(20) NOT NULL DEFAULT 'user',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Seed data for table `users`
INSERT INTO `users` (`id`, `name`, `email`, `mobileno`, `password`, `usertype`) VALUES
(1, 'Admin', 'admin@gmail.com', '1234567890', '123456', 'admin'),
(2, 'Aarav Sharma', 'user@gmail.com', '9876543210', '123456', 'user');

-- --------------------------------------------------------
-- Table structure for table `packages`
-- --------------------------------------------------------

DROP TABLE IF EXISTS `packages`;
CREATE TABLE `packages` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `location` varchar(100) NOT NULL,
  `description` text DEFAULT NULL,
  `price` varchar(50) NOT NULL,
  `latitude` varchar(50) NOT NULL,
  `longitude` varchar(50) NOT NULL,
  `imageResourceId` int(11) NOT NULL DEFAULT 0,
  `rating` varchar(10) NOT NULL DEFAULT '4.8',
  `category` varchar(50) NOT NULL DEFAULT 'City Tour',
  `duration` varchar(50) NOT NULL DEFAULT '1 Day',
  `bestTime` varchar(100) NOT NULL DEFAULT 'Oct - Mar',
  `highlights` text DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Seed data for table `packages`
INSERT INTO `packages` (`id`, `name`, `location`, `description`, `price`, `latitude`, `longitude`, `imageResourceId`, `rating`, `category`, `duration`, `bestTime`, `highlights`) VALUES
(1, 'Gateway of India & Marine Drive', 'Mumbai', 'Iconic waterfront arch monument, ferry ride & sunset promenade walk.', '1499', '18.9220', '72.8347', 0, '4.8', 'Heritage', '1 Day', 'Oct - Mar (Evening)', 'Guided Walk, Harbor Ferry, Photo Stops'),
(2, 'Elephanta Caves Heritage Tour', 'Mumbai', 'UNESCO World Heritage rock-cut cave temples via scenic harbor boat.', '1899', '18.9633', '72.9315', 0, '4.7', 'UNESCO', 'Half Day', 'Nov - Feb (Morning)', 'Ferry Ticket, Heritage Guide, Cave Entry'),
(3, 'Hawa Mahal & Pink City Walk', 'Jaipur', 'Palace of Winds, Jantar Mantar observatory & bustling local bazaars.', '1799', '26.9239', '75.8267', 0, '4.9', 'Royal City', '1 Day', 'Oct - Mar', 'Entry Passes, Local Guide, Chai Tasting'),
(4, 'Amber Palace & Jal Mahal', 'Jaipur', 'Grand hilltop sandstone palace overlooking Maota Lake with cultural show.', '2299', '26.9855', '75.8513', 0, '4.8', 'Palace', '1 Day', 'Oct - Mar', 'Palace Entry, Jeep Ride, Evening Light Show'),
(5, 'Baga Beach & Aguada Sunset', 'Goa', 'Golden beaches, water sports, coastal lighthouse & seaside shacks.', '2499', '15.5553', '73.7517', 0, '4.7', 'Beach', '2 Days', 'Nov - Feb', 'Water Sports, Fort Aguada, Sunset Cruise'),
(6, 'Old Goa Churches & Spice Farm', 'Goa', 'Basilica of Bom Jesus, Portuguese heritage & aromatic spice plantation.', '1999', '15.5009', '73.9116', 0, '4.6', 'Culture', '1 Day', 'Oct - Mar', 'Spice Farm Lunch, Heritage Walk, Transfers'),
(7, 'Shaniwar Wada & Dagdusheth', 'Pune', 'Historic Maratha citadel, bustling old city lanes & cultural landmarks.', '999', '18.5196', '73.8553', 0, '4.5', 'Heritage', 'Half Day', 'Jul - Feb', 'Guided Walk, Entry Ticket, Local Snacks'),
(8, 'Sinhagad Scenic Hilltop', 'Pune', 'Panoramic Sahyadri mountain views, fresh mountain air & local cuisine.', '1199', '18.3663', '73.7559', 0, '4.7', 'Hilltop', '1 Day', 'Jun - Feb', 'Transport, Sunrise Trek, Traditional Lunch'),
(9, 'City Palace & Lake Pichola', 'Udaipur', 'Royal lakeside palace complex with sunset boat cruise on Lake Pichola.', '2599', '24.5764', '73.6835', 0, '4.9', 'Lakeside', '2 Days', 'Sep - Mar', 'Boat Cruise, Palace Museum, Cultural Show'),
(10, 'Taj Mahal Sunrise Experience', 'Agra', 'Guided sunrise visit to the ivory-white marble wonder & Agra Fort.', '2799', '27.1751', '78.0421', 0, '5.0', 'Wonder', '1 Day', 'Oct - Mar (Sunrise)', 'Skip-the-Line Pass, Historian Guide, Transfers'),
(11, 'Red Fort & India Gate Tour', 'Delhi', 'Full-day heritage circuit covering Red Fort, India Gate & Qutub Minar.', '1699', '28.6562', '77.2410', 0, '4.6', 'Capital', '1 Day', 'Oct - Mar', 'AC Cab, Monument Tickets, City Guide'),
(12, 'Solang Valley & Mall Road', 'Manali', 'Snow-capped Himalayan peaks, cable car views & cozy mountain cafes.', '3299', '32.3161', '77.1570', 0, '4.8', 'Mountains', '2 Days', 'Dec - Jun', 'Ropeway Ride, Valley Transfers, Campfire');

-- --------------------------------------------------------
-- Table structure for table `favorites`
-- --------------------------------------------------------

DROP TABLE IF EXISTS `favorites`;
CREATE TABLE `favorites` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `package_id` varchar(50) NOT NULL UNIQUE,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Seed data for table `favorites`
INSERT INTO `favorites` (`id`, `package_id`) VALUES
(1, '3'),
(2, '9');

-- --------------------------------------------------------
-- Table structure for table `bookings`
-- --------------------------------------------------------

DROP TABLE IF EXISTS `bookings`;
CREATE TABLE `bookings` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fort` varchar(255) NOT NULL,
  `location` varchar(100) NOT NULL,
  `username` varchar(150) NOT NULL,
  `mobileno` varchar(20) NOT NULL,
  `address` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Seed data for table `bookings`
INSERT INTO `bookings` (`id`, `fort`, `location`, `username`, `mobileno`, `address`) VALUES
(1, 'Hawa Mahal & Pink City Walk', 'Jaipur', 'Aarav Sharma', '9876543210', 'MG Road, Pune'),
(2, 'Gateway of India & Marine Drive', 'Mumbai', 'Aarav Sharma', '9876543210', 'MG Road, Pune');

-- --------------------------------------------------------
-- Table structure for table `Configuration`
-- --------------------------------------------------------

DROP TABLE IF EXISTS `Configuration`;
CREATE TABLE `Configuration` (
  `CName` varchar(100) DEFAULT NULL,
  `CValue` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
