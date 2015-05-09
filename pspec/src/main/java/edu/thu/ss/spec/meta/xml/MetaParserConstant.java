package edu.thu.ss.spec.meta.xml;

import java.util.Arrays;
import java.util.List;

public interface MetaParserConstant {
  public static final String Meta_Schema_Location = "res/meta.xsd";

  public static final String Ele_Root = "meta-mapping";
  public static final String Ele_UserList = "userList";
  public static final String Ele_User = "user";
  public static final String Ele_Database = "database";
  public static final String Ele_Table = "table";
  public static final String Ele_Column = "column";
  public static final String Ele_Desensitize_Operation = "desensitize-operation";
  public static final String Ele_Desensitize_UDF = "UDF";
  public static final String Ele_Condition = "condition";
  public static final String Ele_Join = "join";
  public static final String Ele_Join_Column = "column";

  public static final String Ele_Struct = "struct";
  public static final String Ele_Map = "map";
  public static final String Ele_Array = "array";
  public static final String Ele_Composite = "composite";

  public static final String Ele_Struct_Field = "field";
  public static final String Ele_Map_Entry = "entry";
  public static final String Ele_Array_Item = "item";
  public static final String Ele_Composite_Extract = "extract-operation";

  public static final String Attr_Name = "name";
  public static final String Attr_userName = "user-name";
  public static final String Attr_Joinable = "joinable";
  public static final String Attr_Multiplicity = "multiplicity";

  public static final String Attr_Data_Category = "data-category";
  public static final String Attr_User_Category = "user-category";
  public static final String Attr_Join_Table = "table";
  public static final String Attr_Join_Target = "target";
  public static final String Attr_Join_Column = "name";

  public static final String Attr_Struct_Field_Name = "name";
  public static final String Attr_Map_Entry_Key = "key";
  public static final String Attr_Array_Item_Index = "index";
  public static final String Attr_Composite_Extract_Name = "name";
  
  public static final String Attr_Complex_Type_Default = "*";


  public static final String Attr_Policy = "policy";

  public static final List<String> ComplexTypes = Arrays.asList(Ele_Struct, Ele_Map, Ele_Array,
      Ele_Composite);

}
