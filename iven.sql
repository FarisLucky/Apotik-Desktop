-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 21, 2018 at 11:21 AM
-- Server version: 10.1.36-MariaDB
-- PHP Version: 5.6.38

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `iven`
--

-- --------------------------------------------------------

--
-- Table structure for table `akun`
--

CREATE TABLE `akun` (
  `Id_Akun` int(4) NOT NULL,
  `Username` varchar(20) NOT NULL,
  `Password` varchar(20) NOT NULL,
  `Nama` char(30) NOT NULL,
  `Jabatan` char(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `akun`
--

INSERT INTO `akun` (`Id_Akun`, `Username`, `Password`, `Nama`, `Jabatan`) VALUES
(1, 'faris', 'riss', 'Salman AL Farisi', 'Admin'),
(2, 'arsyad', 'arsyad', 'Arsyad Arthan Nurrohin', 'Gudang');

-- --------------------------------------------------------

--
-- Table structure for table `detail_pembelian`
--

CREATE TABLE `detail_pembelian` (
  `Id_Pembelian` int(4) NOT NULL,
  `No_batch` varchar(10) NOT NULL,
  `Diskon` int(9) NOT NULL,
  `Harga_beli` int(9) NOT NULL,
  `quantity` int(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `harga`
--

CREATE TABLE `harga` (
  `Id_Harga` int(4) NOT NULL,
  `No_batch` varchar(10) NOT NULL,
  `Harga` int(9) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `kategori_obat`
--

CREATE TABLE `kategori_obat` (
  `Id_Kategori` int(4) NOT NULL,
  `Kategori` char(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `obat`
--

CREATE TABLE `obat` (
  `No_Batch` varchar(10) NOT NULL,
  `Nama_Obat` char(30) NOT NULL,
  `Id_Kategori` int(4) NOT NULL,
  `Id_Satuan` int(4) NOT NULL,
  `Stock` int(4) NOT NULL,
  `Harga_beli` int(9) NOT NULL,
  `Expired` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `pembelian`
--

CREATE TABLE `pembelian` (
  `Id_Pembelian` int(4) NOT NULL,
  `No_Faktur` varchar(10) NOT NULL,
  `Tgl_Transaksi` datetime NOT NULL,
  `Tgl_JatuhTempo` datetime NOT NULL,
  `Id_Supplier` int(4) NOT NULL,
  `Id_Akun` int(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `satuan_obat`
--

CREATE TABLE `satuan_obat` (
  `Id_Satuan` int(4) NOT NULL,
  `Satuan` char(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `supplier`
--

CREATE TABLE `supplier` (
  `Id_Supplier` int(4) NOT NULL,
  `No_PBF` varchar(50) NOT NULL,
  `Nama_PBF` varchar(50) NOT NULL,
  `Alamat` varchar(35) NOT NULL,
  `Telp` varchar(13) NOT NULL,
  `Fax` varchar(15) NOT NULL,
  `Email` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `supplier`
--

INSERT INTO `supplier` (`Id_Supplier`, `No_PBF`, `Nama_PBF`, `Alamat`, `Telp`, `Fax`, `Email`) VALUES
(1, '0824U98', 'PT.KIMIAFARMASI', 'Jl.Mastrip 7', '082213034131', '09120740', 'makana@gmail.com'),
(3, '0997342', 'PT.SASTRAOBAT', 'Jl.Pandawa 5', '082231904821', '09775', 'sastra203@gmail.com');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `akun`
--
ALTER TABLE `akun`
  ADD PRIMARY KEY (`Id_Akun`);

--
-- Indexes for table `detail_pembelian`
--
ALTER TABLE `detail_pembelian`
  ADD KEY `Id_Obat` (`No_batch`),
  ADD KEY `Id_Pembelian` (`Id_Pembelian`);

--
-- Indexes for table `harga`
--
ALTER TABLE `harga`
  ADD PRIMARY KEY (`Id_Harga`),
  ADD KEY `No_batch` (`No_batch`),
  ADD KEY `No_batch_2` (`No_batch`);

--
-- Indexes for table `kategori_obat`
--
ALTER TABLE `kategori_obat`
  ADD PRIMARY KEY (`Id_Kategori`);

--
-- Indexes for table `obat`
--
ALTER TABLE `obat`
  ADD PRIMARY KEY (`No_Batch`),
  ADD KEY `Id_Kategori` (`Id_Kategori`),
  ADD KEY `Id_Satuan` (`Id_Satuan`);

--
-- Indexes for table `pembelian`
--
ALTER TABLE `pembelian`
  ADD PRIMARY KEY (`Id_Pembelian`),
  ADD KEY `Id_Supplier` (`Id_Supplier`),
  ADD KEY `Id_Akun` (`Id_Akun`);

--
-- Indexes for table `satuan_obat`
--
ALTER TABLE `satuan_obat`
  ADD PRIMARY KEY (`Id_Satuan`);

--
-- Indexes for table `supplier`
--
ALTER TABLE `supplier`
  ADD PRIMARY KEY (`Id_Supplier`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `akun`
--
ALTER TABLE `akun`
  MODIFY `Id_Akun` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `detail_pembelian`
--
ALTER TABLE `detail_pembelian`
  MODIFY `Id_Pembelian` int(4) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `harga`
--
ALTER TABLE `harga`
  MODIFY `Id_Harga` int(4) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `kategori_obat`
--
ALTER TABLE `kategori_obat`
  MODIFY `Id_Kategori` int(4) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `pembelian`
--
ALTER TABLE `pembelian`
  MODIFY `Id_Pembelian` int(4) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `satuan_obat`
--
ALTER TABLE `satuan_obat`
  MODIFY `Id_Satuan` int(4) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `supplier`
--
ALTER TABLE `supplier`
  MODIFY `Id_Supplier` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `detail_pembelian`
--
ALTER TABLE `detail_pembelian`
  ADD CONSTRAINT `detail_pembelian_ibfk_1` FOREIGN KEY (`Id_Pembelian`) REFERENCES `pembelian` (`Id_Pembelian`);

--
-- Constraints for table `harga`
--
ALTER TABLE `harga`
  ADD CONSTRAINT `harga_ibfk_1` FOREIGN KEY (`No_batch`) REFERENCES `obat` (`No_Batch`);

--
-- Constraints for table `obat`
--
ALTER TABLE `obat`
  ADD CONSTRAINT `obat_ibfk_1` FOREIGN KEY (`Id_Satuan`) REFERENCES `satuan_obat` (`Id_Satuan`),
  ADD CONSTRAINT `obat_ibfk_2` FOREIGN KEY (`Id_Kategori`) REFERENCES `kategori_obat` (`Id_Kategori`);

--
-- Constraints for table `pembelian`
--
ALTER TABLE `pembelian`
  ADD CONSTRAINT `pembelian_ibfk_1` FOREIGN KEY (`Id_Supplier`) REFERENCES `supplier` (`Id_Supplier`),
  ADD CONSTRAINT `pembelian_ibfk_2` FOREIGN KEY (`Id_Akun`) REFERENCES `akun` (`Id_Akun`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
