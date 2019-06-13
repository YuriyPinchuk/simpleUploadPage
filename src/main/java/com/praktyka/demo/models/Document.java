package com.praktyka.demo.models;

import com.praktyka.demo.utils.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.nio.file.Path;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Document {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    private String path;
    @Enumerated(EnumType.STRING)
    private Type type;
    private long size;
    private LocalDateTime startLocalDateTime;
    private LocalDateTime endLocalDateTime;
}
