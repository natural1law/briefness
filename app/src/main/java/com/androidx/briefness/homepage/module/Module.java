package com.androidx.briefness.homepage.module;// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: com/tools/protobuf/Result.proto

public final class Module {
  private Module() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface ResultOrBuilder extends
      // @@protoc_insertion_point(interface_extends:Result)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <pre>
     *返回码
     * </pre>
     *
     * <code>sint32 code = 1;</code>
     * @return The code.
     */
    int getCode();

    /**
     * <pre>
     *返回信息
     * </pre>
     *
     * <code>string msg = 2;</code>
     * @return The msg.
     */
    String getMsg();
    /**
     * <pre>
     *返回信息
     * </pre>
     *
     * <code>string msg = 2;</code>
     * @return The bytes for msg.
     */
    com.google.protobuf.ByteString
        getMsgBytes();

    /**
     * <pre>
     *签名
     * </pre>
     *
     * <code>string sign = 3;</code>
     * @return The sign.
     */
    String getSign();
    /**
     * <pre>
     *签名
     * </pre>
     *
     * <code>string sign = 3;</code>
     * @return The bytes for sign.
     */
    com.google.protobuf.ByteString
        getSignBytes();

    /**
     * <pre>
     *公钥
     * </pre>
     *
     * <code>string puk = 4;</code>
     * @return The puk.
     */
    String getPuk();
    /**
     * <pre>
     *公钥
     * </pre>
     *
     * <code>string puk = 4;</code>
     * @return The bytes for puk.
     */
    com.google.protobuf.ByteString
        getPukBytes();

    /**
     * <pre>
     *密钥
     * </pre>
     *
     * <code>string token = 5;</code>
     * @return The token.
     */
    String getToken();
    /**
     * <pre>
     *密钥
     * </pre>
     *
     * <code>string token = 5;</code>
     * @return The bytes for token.
     */
    com.google.protobuf.ByteString
        getTokenBytes();

    /**
     * <pre>
     *返回数据
     * </pre>
     *
     * <code>string data = 6;</code>
     * @return The data.
     */
    String getData();
    /**
     * <pre>
     *返回数据
     * </pre>
     *
     * <code>string data = 6;</code>
     * @return The bytes for data.
     */
    com.google.protobuf.ByteString
        getDataBytes();
  }
  /**
   * <pre>
   *响应结构体
   * </pre>
   *
   * Protobuf type {@code Result}
   */
  public static final class Result extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:Result)
      ResultOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use Result.newBuilder() to construct.
    private Result(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private Result() {
      msg_ = "";
      sign_ = "";
      puk_ = "";
      token_ = "";
      data_ = "";
    }

    @Override
    @SuppressWarnings({"unused"})
    protected Object newInstance(
        UnusedPrivateParameter unused) {
      return new Result();
    }

    @Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private Result(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      if (extensionRegistry == null) {
        throw new NullPointerException();
      }
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            case 8: {

              code_ = input.readSInt32();
              break;
            }
            case 18: {
              String s = input.readStringRequireUtf8();

              msg_ = s;
              break;
            }
            case 26: {
              String s = input.readStringRequireUtf8();

              sign_ = s;
              break;
            }
            case 34: {
              String s = input.readStringRequireUtf8();

              puk_ = s;
              break;
            }
            case 42: {
              String s = input.readStringRequireUtf8();

              token_ = s;
              break;
            }
            case 50: {
              String s = input.readStringRequireUtf8();

              data_ = s;
              break;
            }
            default: {
              if (!parseUnknownField(
                  input, unknownFields, extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return Module.internal_static_Result_descriptor;
    }

    @Override
    protected FieldAccessorTable
        internalGetFieldAccessorTable() {
      return Module.internal_static_Result_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              Result.class, Builder.class);
    }

    public static final int CODE_FIELD_NUMBER = 1;
    private int code_;
    /**
     * <pre>
     *返回码
     * </pre>
     *
     * <code>sint32 code = 1;</code>
     * @return The code.
     */
    @Override
    public int getCode() {
      return code_;
    }

    public static final int MSG_FIELD_NUMBER = 2;
    private volatile Object msg_;
    /**
     * <pre>
     *返回信息
     * </pre>
     *
     * <code>string msg = 2;</code>
     * @return The msg.
     */
    @Override
    public String getMsg() {
      Object ref = msg_;
      if (ref instanceof String) {
        return (String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        msg_ = s;
        return s;
      }
    }
    /**
     * <pre>
     *返回信息
     * </pre>
     *
     * <code>string msg = 2;</code>
     * @return The bytes for msg.
     */
    @Override
    public com.google.protobuf.ByteString
        getMsgBytes() {
      Object ref = msg_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        msg_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int SIGN_FIELD_NUMBER = 3;
    private volatile Object sign_;
    /**
     * <pre>
     *签名
     * </pre>
     *
     * <code>string sign = 3;</code>
     * @return The sign.
     */
    @Override
    public String getSign() {
      Object ref = sign_;
      if (ref instanceof String) {
        return (String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        sign_ = s;
        return s;
      }
    }
    /**
     * <pre>
     *签名
     * </pre>
     *
     * <code>string sign = 3;</code>
     * @return The bytes for sign.
     */
    @Override
    public com.google.protobuf.ByteString
        getSignBytes() {
      Object ref = sign_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        sign_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int PUK_FIELD_NUMBER = 4;
    private volatile Object puk_;
    /**
     * <pre>
     *公钥
     * </pre>
     *
     * <code>string puk = 4;</code>
     * @return The puk.
     */
    @Override
    public String getPuk() {
      Object ref = puk_;
      if (ref instanceof String) {
        return (String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        puk_ = s;
        return s;
      }
    }
    /**
     * <pre>
     *公钥
     * </pre>
     *
     * <code>string puk = 4;</code>
     * @return The bytes for puk.
     */
    @Override
    public com.google.protobuf.ByteString
        getPukBytes() {
      Object ref = puk_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        puk_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int TOKEN_FIELD_NUMBER = 5;
    private volatile Object token_;
    /**
     * <pre>
     *密钥
     * </pre>
     *
     * <code>string token = 5;</code>
     * @return The token.
     */
    @Override
    public String getToken() {
      Object ref = token_;
      if (ref instanceof String) {
        return (String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        token_ = s;
        return s;
      }
    }
    /**
     * <pre>
     *密钥
     * </pre>
     *
     * <code>string token = 5;</code>
     * @return The bytes for token.
     */
    @Override
    public com.google.protobuf.ByteString
        getTokenBytes() {
      Object ref = token_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        token_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int DATA_FIELD_NUMBER = 6;
    private volatile Object data_;
    /**
     * <pre>
     *返回数据
     * </pre>
     *
     * <code>string data = 6;</code>
     * @return The data.
     */
    @Override
    public String getData() {
      Object ref = data_;
      if (ref instanceof String) {
        return (String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        data_ = s;
        return s;
      }
    }
    /**
     * <pre>
     *返回数据
     * </pre>
     *
     * <code>string data = 6;</code>
     * @return The bytes for data.
     */
    @Override
    public com.google.protobuf.ByteString
        getDataBytes() {
      Object ref = data_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        data_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    private byte memoizedIsInitialized = -1;
    @Override
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    @Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (code_ != 0) {
        output.writeSInt32(1, code_);
      }
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(msg_)) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 2, msg_);
      }
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(sign_)) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 3, sign_);
      }
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(puk_)) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 4, puk_);
      }
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(token_)) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 5, token_);
      }
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(data_)) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 6, data_);
      }
      unknownFields.writeTo(output);
    }

    @Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (code_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeSInt32Size(1, code_);
      }
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(msg_)) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, msg_);
      }
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(sign_)) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, sign_);
      }
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(puk_)) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(4, puk_);
      }
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(token_)) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(5, token_);
      }
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(data_)) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(6, data_);
      }
      size += unknownFields.getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @Override
    public boolean equals(final Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof Result)) {
        return super.equals(obj);
      }
      Result other = (Result) obj;

      if (getCode()
          != other.getCode()) return false;
      if (!getMsg()
          .equals(other.getMsg())) return false;
      if (!getSign()
          .equals(other.getSign())) return false;
      if (!getPuk()
          .equals(other.getPuk())) return false;
      if (!getToken()
          .equals(other.getToken())) return false;
      if (!getData()
          .equals(other.getData())) return false;
      if (!unknownFields.equals(other.unknownFields)) return false;
      return true;
    }

    @Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      hash = (37 * hash) + CODE_FIELD_NUMBER;
      hash = (53 * hash) + getCode();
      hash = (37 * hash) + MSG_FIELD_NUMBER;
      hash = (53 * hash) + getMsg().hashCode();
      hash = (37 * hash) + SIGN_FIELD_NUMBER;
      hash = (53 * hash) + getSign().hashCode();
      hash = (37 * hash) + PUK_FIELD_NUMBER;
      hash = (53 * hash) + getPuk().hashCode();
      hash = (37 * hash) + TOKEN_FIELD_NUMBER;
      hash = (53 * hash) + getToken().hashCode();
      hash = (37 * hash) + DATA_FIELD_NUMBER;
      hash = (53 * hash) + getData().hashCode();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static Result parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static Result parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static Result parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static Result parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static Result parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static Result parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static Result parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static Result parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static Result parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static Result parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static Result parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static Result parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(Result prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    @Override
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @Override
    protected Builder newBuilderForType(
        BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * <pre>
     *响应结构体
     * </pre>
     *
     * Protobuf type {@code Result}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:Result)
        ResultOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return Module.internal_static_Result_descriptor;
      }

      @Override
      protected FieldAccessorTable
          internalGetFieldAccessorTable() {
        return Module.internal_static_Result_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                Result.class, Builder.class);
      }

      // Construct using Module.Result.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
                .alwaysUseFieldBuilders) {
        }
      }
      @Override
      public Builder clear() {
        super.clear();
        code_ = 0;

        msg_ = "";

        sign_ = "";

        puk_ = "";

        token_ = "";

        data_ = "";

        return this;
      }

      @Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return Module.internal_static_Result_descriptor;
      }

      @Override
      public Result getDefaultInstanceForType() {
        return Result.getDefaultInstance();
      }

      @Override
      public Result build() {
        Result result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @Override
      public Result buildPartial() {
        Result result = new Result(this);
        result.code_ = code_;
        result.msg_ = msg_;
        result.sign_ = sign_;
        result.puk_ = puk_;
        result.token_ = token_;
        result.data_ = data_;
        onBuilt();
        return result;
      }

      @Override
      public Builder clone() {
        return super.clone();
      }
      @Override
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          Object value) {
        return super.setField(field, value);
      }
      @Override
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return super.clearField(field);
      }
      @Override
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return super.clearOneof(oneof);
      }
      @Override
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, Object value) {
        return super.setRepeatedField(field, index, value);
      }
      @Override
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          Object value) {
        return super.addRepeatedField(field, value);
      }
      @Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof Result) {
          return mergeFrom((Result)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(Result other) {
        if (other == Result.getDefaultInstance()) return this;
        if (other.getCode() != 0) {
          setCode(other.getCode());
        }
        if (!other.getMsg().isEmpty()) {
          msg_ = other.msg_;
          onChanged();
        }
        if (!other.getSign().isEmpty()) {
          sign_ = other.sign_;
          onChanged();
        }
        if (!other.getPuk().isEmpty()) {
          puk_ = other.puk_;
          onChanged();
        }
        if (!other.getToken().isEmpty()) {
          token_ = other.token_;
          onChanged();
        }
        if (!other.getData().isEmpty()) {
          data_ = other.data_;
          onChanged();
        }
        this.mergeUnknownFields(other.unknownFields);
        onChanged();
        return this;
      }

      @Override
      public final boolean isInitialized() {
        return true;
      }

      @Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        Result parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (Result) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private int code_ ;
      /**
       * <pre>
       *返回码
       * </pre>
       *
       * <code>sint32 code = 1;</code>
       * @return The code.
       */
      @Override
      public int getCode() {
        return code_;
      }
      /**
       * <pre>
       *返回码
       * </pre>
       *
       * <code>sint32 code = 1;</code>
       * @param value The code to set.
       * @return This builder for chaining.
       */
      public Builder setCode(int value) {
        
        code_ = value;
        onChanged();
        return this;
      }
      /**
       * <pre>
       *返回码
       * </pre>
       *
       * <code>sint32 code = 1;</code>
       * @return This builder for chaining.
       */
      public Builder clearCode() {
        
        code_ = 0;
        onChanged();
        return this;
      }

      private Object msg_ = "";
      /**
       * <pre>
       *返回信息
       * </pre>
       *
       * <code>string msg = 2;</code>
       * @return The msg.
       */
      public String getMsg() {
        Object ref = msg_;
        if (!(ref instanceof String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          String s = bs.toStringUtf8();
          msg_ = s;
          return s;
        } else {
          return (String) ref;
        }
      }
      /**
       * <pre>
       *返回信息
       * </pre>
       *
       * <code>string msg = 2;</code>
       * @return The bytes for msg.
       */
      public com.google.protobuf.ByteString
          getMsgBytes() {
        Object ref = msg_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (String) ref);
          msg_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <pre>
       *返回信息
       * </pre>
       *
       * <code>string msg = 2;</code>
       * @param value The msg to set.
       * @return This builder for chaining.
       */
      public Builder setMsg(
          String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        msg_ = value;
        onChanged();
        return this;
      }
      /**
       * <pre>
       *返回信息
       * </pre>
       *
       * <code>string msg = 2;</code>
       * @return This builder for chaining.
       */
      public Builder clearMsg() {
        
        msg_ = getDefaultInstance().getMsg();
        onChanged();
        return this;
      }
      /**
       * <pre>
       *返回信息
       * </pre>
       *
       * <code>string msg = 2;</code>
       * @param value The bytes for msg to set.
       * @return This builder for chaining.
       */
      public Builder setMsgBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        msg_ = value;
        onChanged();
        return this;
      }

      private Object sign_ = "";
      /**
       * <pre>
       *签名
       * </pre>
       *
       * <code>string sign = 3;</code>
       * @return The sign.
       */
      public String getSign() {
        Object ref = sign_;
        if (!(ref instanceof String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          String s = bs.toStringUtf8();
          sign_ = s;
          return s;
        } else {
          return (String) ref;
        }
      }
      /**
       * <pre>
       *签名
       * </pre>
       *
       * <code>string sign = 3;</code>
       * @return The bytes for sign.
       */
      public com.google.protobuf.ByteString
          getSignBytes() {
        Object ref = sign_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (String) ref);
          sign_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <pre>
       *签名
       * </pre>
       *
       * <code>string sign = 3;</code>
       * @param value The sign to set.
       * @return This builder for chaining.
       */
      public Builder setSign(
          String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        sign_ = value;
        onChanged();
        return this;
      }
      /**
       * <pre>
       *签名
       * </pre>
       *
       * <code>string sign = 3;</code>
       * @return This builder for chaining.
       */
      public Builder clearSign() {
        
        sign_ = getDefaultInstance().getSign();
        onChanged();
        return this;
      }
      /**
       * <pre>
       *签名
       * </pre>
       *
       * <code>string sign = 3;</code>
       * @param value The bytes for sign to set.
       * @return This builder for chaining.
       */
      public Builder setSignBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        sign_ = value;
        onChanged();
        return this;
      }

      private Object puk_ = "";
      /**
       * <pre>
       *公钥
       * </pre>
       *
       * <code>string puk = 4;</code>
       * @return The puk.
       */
      public String getPuk() {
        Object ref = puk_;
        if (!(ref instanceof String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          String s = bs.toStringUtf8();
          puk_ = s;
          return s;
        } else {
          return (String) ref;
        }
      }
      /**
       * <pre>
       *公钥
       * </pre>
       *
       * <code>string puk = 4;</code>
       * @return The bytes for puk.
       */
      public com.google.protobuf.ByteString
          getPukBytes() {
        Object ref = puk_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (String) ref);
          puk_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <pre>
       *公钥
       * </pre>
       *
       * <code>string puk = 4;</code>
       * @param value The puk to set.
       * @return This builder for chaining.
       */
      public Builder setPuk(
          String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        puk_ = value;
        onChanged();
        return this;
      }
      /**
       * <pre>
       *公钥
       * </pre>
       *
       * <code>string puk = 4;</code>
       * @return This builder for chaining.
       */
      public Builder clearPuk() {
        
        puk_ = getDefaultInstance().getPuk();
        onChanged();
        return this;
      }
      /**
       * <pre>
       *公钥
       * </pre>
       *
       * <code>string puk = 4;</code>
       * @param value The bytes for puk to set.
       * @return This builder for chaining.
       */
      public Builder setPukBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        puk_ = value;
        onChanged();
        return this;
      }

      private Object token_ = "";
      /**
       * <pre>
       *密钥
       * </pre>
       *
       * <code>string token = 5;</code>
       * @return The token.
       */
      public String getToken() {
        Object ref = token_;
        if (!(ref instanceof String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          String s = bs.toStringUtf8();
          token_ = s;
          return s;
        } else {
          return (String) ref;
        }
      }
      /**
       * <pre>
       *密钥
       * </pre>
       *
       * <code>string token = 5;</code>
       * @return The bytes for token.
       */
      public com.google.protobuf.ByteString
          getTokenBytes() {
        Object ref = token_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (String) ref);
          token_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <pre>
       *密钥
       * </pre>
       *
       * <code>string token = 5;</code>
       * @param value The token to set.
       * @return This builder for chaining.
       */
      public Builder setToken(
          String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        token_ = value;
        onChanged();
        return this;
      }
      /**
       * <pre>
       *密钥
       * </pre>
       *
       * <code>string token = 5;</code>
       * @return This builder for chaining.
       */
      public Builder clearToken() {
        
        token_ = getDefaultInstance().getToken();
        onChanged();
        return this;
      }
      /**
       * <pre>
       *密钥
       * </pre>
       *
       * <code>string token = 5;</code>
       * @param value The bytes for token to set.
       * @return This builder for chaining.
       */
      public Builder setTokenBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        token_ = value;
        onChanged();
        return this;
      }

      private Object data_ = "";
      /**
       * <pre>
       *返回数据
       * </pre>
       *
       * <code>string data = 6;</code>
       * @return The data.
       */
      public String getData() {
        Object ref = data_;
        if (!(ref instanceof String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          String s = bs.toStringUtf8();
          data_ = s;
          return s;
        } else {
          return (String) ref;
        }
      }
      /**
       * <pre>
       *返回数据
       * </pre>
       *
       * <code>string data = 6;</code>
       * @return The bytes for data.
       */
      public com.google.protobuf.ByteString
          getDataBytes() {
        Object ref = data_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (String) ref);
          data_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <pre>
       *返回数据
       * </pre>
       *
       * <code>string data = 6;</code>
       * @param value The data to set.
       * @return This builder for chaining.
       */
      public Builder setData(
          String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        data_ = value;
        onChanged();
        return this;
      }
      /**
       * <pre>
       *返回数据
       * </pre>
       *
       * <code>string data = 6;</code>
       * @return This builder for chaining.
       */
      public Builder clearData() {
        
        data_ = getDefaultInstance().getData();
        onChanged();
        return this;
      }
      /**
       * <pre>
       *返回数据
       * </pre>
       *
       * <code>string data = 6;</code>
       * @param value The bytes for data to set.
       * @return This builder for chaining.
       */
      public Builder setDataBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        data_ = value;
        onChanged();
        return this;
      }
      @Override
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFields(unknownFields);
      }

      @Override
      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:Result)
    }

    // @@protoc_insertion_point(class_scope:Result)
    private static final Result DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new Result();
    }

    public static Result getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<Result>
        PARSER = new com.google.protobuf.AbstractParser<Result>() {
      @Override
      public Result parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new Result(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<Result> parser() {
      return PARSER;
    }

    @Override
    public com.google.protobuf.Parser<Result> getParserForType() {
      return PARSER;
    }

    @Override
    public Result getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_Result_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_Result_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    String[] descriptorData = {
      "\n\037com/tools/protobuf/Result.proto\"[\n\006Res" +
      "ult\022\014\n\004code\030\001 \001(\021\022\013\n\003msg\030\002 \001(\t\022\014\n\004sign\030\003" +
      " \001(\t\022\013\n\003puk\030\004 \001(\t\022\r\n\005token\030\005 \001(\t\022\014\n\004data" +
      "\030\006 \001(\tB\010B\006Moduleb\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_Result_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_Result_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_Result_descriptor,
        new String[] { "Code", "Msg", "Sign", "Puk", "Token", "Data", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
