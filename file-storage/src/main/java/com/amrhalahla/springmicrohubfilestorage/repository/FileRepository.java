package com.amrhalahla.springmicrohubfilestorage.repository;

import com.amrhalahla.springmicrohubfilestorage.model.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, String> {
}
