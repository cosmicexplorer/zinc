/*
 * Zinc - The incremental compiler for Scala.
 * Copyright Lightbend, Inc. and Mark Harrah
 *
 * Licensed under Apache License 2.0
 * (http://www.apache.org/licenses/LICENSE-2.0).
 *
 * See the NOTICE file distributed with this work for
 * additional information regarding copyright ownership.
 */

package xsbti.compile;

import xsbti.compile.analysis.ReadWriteMappers;

import java.io.File;

/**
 * Defines a store interface that provides analysis **file** read and write capabilities to users.
 *
 * This interface provides a backend for `AnalysisStore` to read and write from files,
 * storing the analysis contents in the file system before or after every incremental compile.
 */
public interface FileAnalysisStore extends AnalysisStore {
    /**
     * Returns the default implementation of a file-based `AnalysisStore`.
     *
     * This implementation is binary and based on Protobuf, which means that the content
     * of the file will be binary and can be read in plain text with the Protobuf toolkit.
     *
     * @param analysisFile The analysis file to store.
     * @return A binary file-based analysis store.
     */
    static AnalysisStore getDefault(File analysisFile) {
        return sbt.internal.inc.FileAnalysisStore.binary(analysisFile);
    }

    /**
     * Returns the default implementation of a file-based `AnalysisStore`.
     *
     * This implementation is binary and based on Protobuf, which means that the content
     * of the file will be binary and can be read in plain text with the Protobuf toolkit.
     *
     * @param analysisFile The analysis file to store.
     * @param mappers The mappers to be used while reading and writing the analysis file.
     * @return A binary file-based analysis store.
     */
    static AnalysisStore getDefault(File analysisFile, ReadWriteMappers mappers) {
        return sbt.internal.inc.FileAnalysisStore.binary(analysisFile, mappers);
    }
}