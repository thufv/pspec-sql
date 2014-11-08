package org.apache.spark.sql.parquet;
// no position
private  class ParquetTypesConverter$ implements org.apache.spark.Logging {
  /**
   * Static reference to the singleton instance of this Scala object.
   */
  public static final ParquetTypesConverter$ MODULE$ = null;
  public   ParquetTypesConverter$ () { throw new RuntimeException(); }
  public  boolean isPrimitiveType (org.apache.spark.sql.catalyst.types.DataType ctype) { throw new RuntimeException(); }
  public  org.apache.spark.sql.catalyst.types.DataType toPrimitiveDataType (parquet.schema.PrimitiveType parquetType, boolean binayAsString) { throw new RuntimeException(); }
  /**
   * Converts a given Parquet <code>Type</code> into the corresponding
   * {@link org.apache.spark.sql.catalyst.types.DataType}.
   * <p>
   * We apply the following conversion rules:
   * <ul>
   *   <li> Primitive types are converter to the corresponding primitive type.</li>
   *   <li> Group types that have a single field that is itself a group, which has repetition
   *        level <code>REPEATED</code>, are treated as follows:<ul>
   *          <li> If the nested group has name <code>values</code>, the surrounding group is converted
   *               into an {@link ArrayType} with the corresponding field type (primitive or
   *               complex) as element type.</li>
   *          <li> If the nested group has name <code>map</code> and two fields (named <code>key</code> and <code>value</code>),
   *               the surrounding group is converted into a {@link MapType}
   *               with the corresponding key and value (value possibly complex) types.
   *               Note that we currently assume map values are not nullable.</li>
   *   <li> Other group types are converted into a {@link StructType} with the corresponding
   *        field types.</li></ul></li>
   * </ul>
   * Note that fields are determined to be <code>nullable</code> if and only if their Parquet repetition
   * level is not <code>REQUIRED</code>.
   * <p>
   * @param parquetType The type to convert.
   * @return The corresponding Catalyst type.
   */
  public  org.apache.spark.sql.catalyst.types.DataType toDataType (parquet.schema.Type parquetType, boolean isBinaryAsString) { throw new RuntimeException(); }
  /**
   * For a given Catalyst {@link org.apache.spark.sql.catalyst.types.DataType} return
   * the name of the corresponding Parquet primitive type or None if the given type
   * is not primitive.
   * <p>
   * @param ctype The type to convert
   * @return The name of the corresponding Parquet primitive type
   */
  public  scala.Option<scala.Tuple2<parquet.schema.PrimitiveType.PrimitiveTypeName, scala.Option<parquet.schema.OriginalType>>> fromPrimitiveDataType (org.apache.spark.sql.catalyst.types.DataType ctype) { throw new RuntimeException(); }
  /**
   * Converts a given Catalyst {@link org.apache.spark.sql.catalyst.types.DataType} into
   * the corresponding Parquet <code>Type</code>.
   * <p>
   * The conversion follows the rules below:
   * <ul>
   *   <li> Primitive types are converted into Parquet's primitive types.</li>
   *   <li> {@link org.apache.spark.sql.catalyst.types.StructType}s are converted
   *        into Parquet's <code>GroupType</code> with the corresponding field types.</li>
   *   <li> {@link org.apache.spark.sql.catalyst.types.ArrayType}s are converted
   *        into a 2-level nested group, where the outer group has the inner
   *        group as sole field. The inner group has name <code>values</code> and
   *        repetition level <code>REPEATED</code> and has the element type of
   *        the array as schema. We use Parquet's <code>ConversionPatterns</code> for this
   *        purpose.</li>
   *   <li> {@link org.apache.spark.sql.catalyst.types.MapType}s are converted
   *        into a nested (2-level) Parquet <code>GroupType</code> with two fields: a key
   *        type and a value type. The nested group has repetition level
   *        <code>REPEATED</code> and name <code>map</code>. We use Parquet's <code>ConversionPatterns</code>
   *        for this purpose</li>
   * </ul>
   * Parquet's repetition level is generally set according to the following rule:
   * <ul>
   *   <li> If the call to <code>fromDataType</code> is recursive inside an enclosing <code>ArrayType</code> or
   *   <code>MapType</code>, then the repetition level is set to <code>REPEATED</code>.</li>
   *   <li> Otherwise, if the attribute whose type is converted is <code>nullable</code>, the Parquet
   *   type gets repetition level <code>OPTIONAL</code> and otherwise <code>REQUIRED</code>.</li>
   * </ul>
   * <p>
   *@param ctype The type to convert
   * @param name The name of the {@link org.apache.spark.sql.catalyst.expressions.Attribute}
   *             whose type is converted
   * @param nullable When true indicates that the attribute is nullable
   * @param inArray When true indicates that this is a nested attribute inside an array.
   * @return The corresponding Parquet type.
   */
  public  parquet.schema.Type fromDataType (org.apache.spark.sql.catalyst.types.DataType ctype, java.lang.String name, boolean nullable, boolean inArray) { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> convertToAttributes (parquet.schema.Type parquetSchema, boolean isBinaryAsString) { throw new RuntimeException(); }
  public  parquet.schema.MessageType convertFromAttributes (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> attributes) { throw new RuntimeException(); }
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> convertFromString (java.lang.String string) { throw new RuntimeException(); }
  public  java.lang.String convertToString (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> schema) { throw new RuntimeException(); }
  public  void writeMetaData (scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> attributes, org.apache.hadoop.fs.Path origPath, org.apache.hadoop.conf.Configuration conf) { throw new RuntimeException(); }
  /**
   * Try to read Parquet metadata at the given Path. We first see if there is a summary file
   * in the parent directory. If so, this is used. Else we read the actual footer at the given
   * location.
   * @param origPath The path at which we expect one (or more) Parquet files.
   * @param configuration The Hadoop configuration to use.
   * @return The <code>ParquetMetadata</code> containing among other things the schema.
   */
  public  parquet.hadoop.metadata.ParquetMetadata readMetaData (org.apache.hadoop.fs.Path origPath, scala.Option<org.apache.hadoop.conf.Configuration> configuration) { throw new RuntimeException(); }
  /**
   * Reads in Parquet Metadata from the given path and tries to extract the schema
   * (Catalyst attributes) from the application-specific key-value map. If this
   * is empty it falls back to converting from the Parquet file schema which
   * may lead to an upcast of types (e.g., {byte, short} to int).
   * <p>
   * @param origPath The path at which we expect one (or more) Parquet files.
   * @param conf The Hadoop configuration to use.
   * @return A list of attributes that make up the schema.
   */
  public  scala.collection.Seq<org.apache.spark.sql.catalyst.expressions.Attribute> readSchemaFromFile (org.apache.hadoop.fs.Path origPath, scala.Option<org.apache.hadoop.conf.Configuration> conf, boolean isBinaryAsString) { throw new RuntimeException(); }
}
