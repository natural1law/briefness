package com.androidx.http.module;// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: com/lingke/idphoto/protobuf/Msg.proto

public final class MsgModule {
  private MsgModule() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface MsgOrBuilder extends
      // @@protoc_insertion_point(interface_extends:Msg)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <pre>
     *请求码
     * </pre>
     *
     * <code>sint32 code = 1;</code>
     * @return The code.
     */
    int getCode();

    /**
     * <pre>
     *用户ID
     * </pre>
     *
     * <code>string userId = 2;</code>
     * @return The userId.
     */
    String getUserId();
    /**
     * <pre>
     *用户ID
     * </pre>
     *
     * <code>string userId = 2;</code>
     * @return The bytes for userId.
     */
    com.google.protobuf.ByteString
        getUserIdBytes();

    /**
     * <pre>
     *数据信息
     * </pre>
     *
     * <code>bytes data = 3;</code>
     * @return The data.
     */
    com.google.protobuf.ByteString getData();

    /**
     * <pre>
     *发送时间
     * </pre>
     *
     * <code>string sendTime = 4;</code>
     * @return The sendTime.
     */
    String getSendTime();
    /**
     * <pre>
     *发送时间
     * </pre>
     *
     * <code>string sendTime = 4;</code>
     * @return The bytes for sendTime.
     */
    com.google.protobuf.ByteString
        getSendTimeBytes();
  }
  /**
   * <pre>
   *发送图片请求结构体
   * </pre>
   *
   * Protobuf type {@code Msg}
   */
  public static final class Msg extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:Msg)
      MsgOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use Msg.newBuilder() to construct.
    private Msg(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private Msg() {
      userId_ = "";
      data_ = com.google.protobuf.ByteString.EMPTY;
      sendTime_ = "";
    }

    @Override
    protected Object newInstance(
        UnusedPrivateParameter unused) {
      return new Msg();
    }

    @Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private Msg(
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

              userId_ = input.readStringRequireUtf8();
              break;
            }
            case 26: {

              data_ = input.readBytes();
              break;
            }
            case 34: {

              sendTime_ = input.readStringRequireUtf8();
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
    public static com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return MsgModule.internal_static_Msg_descriptor;
    }

    @Override
    protected FieldAccessorTable
        internalGetFieldAccessorTable() {
      return MsgModule.internal_static_Msg_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              Msg.class, Builder.class);
    }

    public static final int CODE_FIELD_NUMBER = 1;
    private int code_;
    /**
     * <pre>
     *请求码
     * </pre>
     *
     * <code>sint32 code = 1;</code>
     * @return The code.
     */
    @Override
    public int getCode() {
      return code_;
    }

    public static final int USERID_FIELD_NUMBER = 2;
    private volatile Object userId_;
    /**
     * <pre>
     *用户ID
     * </pre>
     *
     * <code>string userId = 2;</code>
     * @return The userId.
     */
    @Override
    public String getUserId() {
      Object ref = userId_;
      if (ref instanceof String) {
        return (String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        userId_ = s;
        return s;
      }
    }
    /**
     * <pre>
     *用户ID
     * </pre>
     *
     * <code>string userId = 2;</code>
     * @return The bytes for userId.
     */
    @Override
    public com.google.protobuf.ByteString
        getUserIdBytes() {
      Object ref = userId_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        userId_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int DATA_FIELD_NUMBER = 3;
    private com.google.protobuf.ByteString data_;
    /**
     * <pre>
     *数据信息
     * </pre>
     *
     * <code>bytes data = 3;</code>
     * @return The data.
     */
    @Override
    public com.google.protobuf.ByteString getData() {
      return data_;
    }

    public static final int SENDTIME_FIELD_NUMBER = 4;
    private volatile Object sendTime_;
    /**
     * <pre>
     *发送时间
     * </pre>
     *
     * <code>string sendTime = 4;</code>
     * @return The sendTime.
     */
    @Override
    public String getSendTime() {
      Object ref = sendTime_;
      if (ref instanceof String) {
        return (String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        sendTime_ = s;
        return s;
      }
    }
    /**
     * <pre>
     *发送时间
     * </pre>
     *
     * <code>string sendTime = 4;</code>
     * @return The bytes for sendTime.
     */
    @Override
    public com.google.protobuf.ByteString
        getSendTimeBytes() {
      Object ref = sendTime_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        sendTime_ = b;
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
      if (!getUserIdBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 2, userId_);
      }
      if (!data_.isEmpty()) {
        output.writeBytes(3, data_);
      }
      if (!getSendTimeBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 4, sendTime_);
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
      if (!getUserIdBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, userId_);
      }
      if (!data_.isEmpty()) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(3, data_);
      }
      if (!getSendTimeBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(4, sendTime_);
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
      if (!(obj instanceof Msg)) {
        return super.equals(obj);
      }
      Msg other = (Msg) obj;

      if (getCode()
          != other.getCode()) return false;
      if (!getUserId()
          .equals(other.getUserId())) return false;
      if (!getData()
          .equals(other.getData())) return false;
      if (!getSendTime()
          .equals(other.getSendTime())) return false;
      return unknownFields.equals(other.unknownFields);
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
      hash = (37 * hash) + USERID_FIELD_NUMBER;
      hash = (53 * hash) + getUserId().hashCode();
      hash = (37 * hash) + DATA_FIELD_NUMBER;
      hash = (53 * hash) + getData().hashCode();
      hash = (37 * hash) + SENDTIME_FIELD_NUMBER;
      hash = (53 * hash) + getSendTime().hashCode();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static Msg parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static Msg parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static Msg parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static Msg parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static Msg parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static Msg parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static Msg parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static Msg parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static Msg parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static Msg parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static Msg parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static Msg parseFrom(
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
    public static Builder newBuilder(Msg prototype) {
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
      return new Builder(parent);
    }
    /**
     * <pre>
     *发送图片请求结构体
     * </pre>
     *
     * Protobuf type {@code Msg}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:Msg)
        MsgOrBuilder {
      public static com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return MsgModule.internal_static_Msg_descriptor;
      }

      @Override
      protected FieldAccessorTable
          internalGetFieldAccessorTable() {
        return MsgModule.internal_static_Msg_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                Msg.class, Builder.class);
      }

      // Construct using MsgModule.Msg.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
      }
      @Override
      public Builder clear() {
        super.clear();
        code_ = 0;

        userId_ = "";

        data_ = com.google.protobuf.ByteString.EMPTY;

        sendTime_ = "";

        return this;
      }

      @Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return MsgModule.internal_static_Msg_descriptor;
      }

      @Override
      public Msg getDefaultInstanceForType() {
        return Msg.getDefaultInstance();
      }

      @Override
      public Msg build() {
        Msg result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @Override
      public Msg buildPartial() {
        Msg result = new Msg(this);
        result.code_ = code_;
        result.userId_ = userId_;
        result.data_ = data_;
        result.sendTime_ = sendTime_;
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
        if (other instanceof Msg) {
          return mergeFrom((Msg)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(Msg other) {
        if (other == Msg.getDefaultInstance()) return this;
        if (other.getCode() != 0) {
          setCode(other.getCode());
        }
        if (!other.getUserId().isEmpty()) {
          userId_ = other.userId_;
          onChanged();
        }
        if (other.getData() != com.google.protobuf.ByteString.EMPTY) {
          setData(other.getData());
        }
        if (!other.getSendTime().isEmpty()) {
          sendTime_ = other.sendTime_;
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
        Msg parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (Msg) e.getUnfinishedMessage();
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
       *请求码
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
       *请求码
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
       *请求码
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

      private Object userId_ = "";
      /**
       * <pre>
       *用户ID
       * </pre>
       *
       * <code>string userId = 2;</code>
       * @return The userId.
       */
      public String getUserId() {
        Object ref = userId_;
        if (!(ref instanceof String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          String s = bs.toStringUtf8();
          userId_ = s;
          return s;
        } else {
          return (String) ref;
        }
      }
      /**
       * <pre>
       *用户ID
       * </pre>
       *
       * <code>string userId = 2;</code>
       * @return The bytes for userId.
       */
      public com.google.protobuf.ByteString
          getUserIdBytes() {
        Object ref = userId_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (String) ref);
          userId_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <pre>
       *用户ID
       * </pre>
       *
       * <code>string userId = 2;</code>
       * @param value The userId to set.
       * @return This builder for chaining.
       */
      public Builder setUserId(
          String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        userId_ = value;
        onChanged();
        return this;
      }
      /**
       * <pre>
       *用户ID
       * </pre>
       *
       * <code>string userId = 2;</code>
       * @return This builder for chaining.
       */
      public Builder clearUserId() {
        
        userId_ = getDefaultInstance().getUserId();
        onChanged();
        return this;
      }
      /**
       * <pre>
       *用户ID
       * </pre>
       *
       * <code>string userId = 2;</code>
       * @param value The bytes for userId to set.
       * @return This builder for chaining.
       */
      public Builder setUserIdBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        userId_ = value;
        onChanged();
        return this;
      }

      private com.google.protobuf.ByteString data_ = com.google.protobuf.ByteString.EMPTY;
      /**
       * <pre>
       *数据信息
       * </pre>
       *
       * <code>bytes data = 3;</code>
       * @return The data.
       */
      @Override
      public com.google.protobuf.ByteString getData() {
        return data_;
      }
      /**
       * <pre>
       *数据信息
       * </pre>
       *
       * <code>bytes data = 3;</code>
       * @param value The data to set.
       * @return This builder for chaining.
       */
      public Builder setData(com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        data_ = value;
        onChanged();
        return this;
      }
      /**
       * <pre>
       *数据信息
       * </pre>
       *
       * <code>bytes data = 3;</code>
       * @return This builder for chaining.
       */
      public Builder clearData() {
        
        data_ = getDefaultInstance().getData();
        onChanged();
        return this;
      }

      private Object sendTime_ = "";
      /**
       * <pre>
       *发送时间
       * </pre>
       *
       * <code>string sendTime = 4;</code>
       * @return The sendTime.
       */
      public String getSendTime() {
        Object ref = sendTime_;
        if (!(ref instanceof String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          String s = bs.toStringUtf8();
          sendTime_ = s;
          return s;
        } else {
          return (String) ref;
        }
      }
      /**
       * <pre>
       *发送时间
       * </pre>
       *
       * <code>string sendTime = 4;</code>
       * @return The bytes for sendTime.
       */
      public com.google.protobuf.ByteString
          getSendTimeBytes() {
        Object ref = sendTime_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (String) ref);
          sendTime_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <pre>
       *发送时间
       * </pre>
       *
       * <code>string sendTime = 4;</code>
       * @param value The sendTime to set.
       * @return This builder for chaining.
       */
      public Builder setSendTime(
          String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        sendTime_ = value;
        onChanged();
        return this;
      }
      /**
       * <pre>
       *发送时间
       * </pre>
       *
       * <code>string sendTime = 4;</code>
       * @return This builder for chaining.
       */
      public Builder clearSendTime() {
        
        sendTime_ = getDefaultInstance().getSendTime();
        onChanged();
        return this;
      }
      /**
       * <pre>
       *发送时间
       * </pre>
       *
       * <code>string sendTime = 4;</code>
       * @param value The bytes for sendTime to set.
       * @return This builder for chaining.
       */
      public Builder setSendTimeBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        sendTime_ = value;
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


      // @@protoc_insertion_point(builder_scope:Msg)
    }

    // @@protoc_insertion_point(class_scope:Msg)
    private static final Msg DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new Msg();
    }

    public static Msg getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<Msg>
        PARSER = new com.google.protobuf.AbstractParser<Msg>() {
      @Override
      public Msg parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new Msg(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<Msg> parser() {
      return PARSER;
    }

    @Override
    public com.google.protobuf.Parser<Msg> getParserForType() {
      return PARSER;
    }

    @Override
    public Msg getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_Msg_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_Msg_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    String[] descriptorData = {
      "\n%com/lingke/idphoto/protobuf/Msg.proto\"" +
      "C\n\003Msg\022\014\n\004code\030\001 \001(\021\022\016\n\006userId\030\002 \001(\t\022\014\n\004" +
      "data\030\003 \001(\014\022\020\n\010sendTime\030\004 \001(\tB\013B\tMsgModul" +
      "eb\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_Msg_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_Msg_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_Msg_descriptor,
        new String[] { "Code", "UserId", "Data", "SendTime", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
