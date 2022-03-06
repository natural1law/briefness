package com.androidx.briefness.homepage.module;// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: com.module.protobuf/SendModule.proto

public final class SendModule {
    private SendModule() {
    }

    public static void registerAllExtensions(
            com.google.protobuf.ExtensionRegistryLite registry) {
    }

    public interface SendRequestOrBuilder extends
            // @@protoc_insertion_point(interface_extends:SendRequest)
            com.google.protobuf.MessageLiteOrBuilder {

        /**
         * <code>string token = 1;</code>
         *
         * @return The token.
         */
        String getToken();

        /**
         * <code>string token = 1;</code>
         *
         * @return The bytes for token.
         */
        com.google.protobuf.ByteString
        getTokenBytes();

        /**
         * <code>string bulking = 2;</code>
         *
         * @return The bulking.
         */
        String getBulking();

        /**
         * <code>string bulking = 2;</code>
         *
         * @return The bytes for bulking.
         */
        com.google.protobuf.ByteString
        getBulkingBytes();

        /**
         * <code>bytes data = 3;</code>
         *
         * @return The data.
         */
        com.google.protobuf.ByteString getData();
    }

    /**
     * Protobuf type {@code SendRequest}
     */
    public static final class SendRequest extends
            com.google.protobuf.GeneratedMessageLite<
                    SendRequest, SendRequest.Builder> implements
            // @@protoc_insertion_point(message_implements:SendRequest)
            SendRequestOrBuilder {
        // @@protoc_insertion_point(class_scope:SendRequest)
        private static final SendRequest DEFAULT_INSTANCE;
        public static final int TOKEN_FIELD_NUMBER = 1;
        private static volatile com.google.protobuf.Parser<SendRequest> PARSER;

        static {
            SendRequest defaultInstance = new SendRequest();
            // New instances are implicitly immutable so no need to make
            // immutable.
            DEFAULT_INSTANCE = defaultInstance;
            com.google.protobuf.GeneratedMessageLite.registerDefaultInstance(
                    SendRequest.class, defaultInstance);
        }

        private String token_;
        private String bulking_;

        private SendRequest() {
            token_ = "";
            bulking_ = "";
            data_ = com.google.protobuf.ByteString.EMPTY;
        }

        public static SendRequest parseFrom(
                java.nio.ByteBuffer data)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return com.google.protobuf.GeneratedMessageLite.parseFrom(
                    DEFAULT_INSTANCE, data);
        }

        public static final int BULKING_FIELD_NUMBER = 2;

        public static SendRequest parseFrom(
                java.nio.ByteBuffer data,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return com.google.protobuf.GeneratedMessageLite.parseFrom(
                    DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static SendRequest parseFrom(
                com.google.protobuf.ByteString data)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return com.google.protobuf.GeneratedMessageLite.parseFrom(
                    DEFAULT_INSTANCE, data);
        }

        public static SendRequest parseFrom(
                com.google.protobuf.ByteString data,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return com.google.protobuf.GeneratedMessageLite.parseFrom(
                    DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static SendRequest parseFrom(byte[] data)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return com.google.protobuf.GeneratedMessageLite.parseFrom(
                    DEFAULT_INSTANCE, data);
        }

        public static SendRequest parseFrom(
                byte[] data,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return com.google.protobuf.GeneratedMessageLite.parseFrom(
                    DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static SendRequest parseFrom(java.io.InputStream input)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageLite.parseFrom(
                    DEFAULT_INSTANCE, input);
        }

        public static final int DATA_FIELD_NUMBER = 3;
        private com.google.protobuf.ByteString data_;

        /**
         * <code>bytes data = 3;</code>
         * @return The data.
         */
        @Override
        public com.google.protobuf.ByteString getData() {
            return data_;
        }

        public static SendRequest parseFrom(
                java.io.InputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageLite.parseFrom(
                    DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static SendRequest parseDelimitedFrom(java.io.InputStream input)
                throws java.io.IOException {
            return parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static SendRequest parseDelimitedFrom(
                java.io.InputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static SendRequest parseFrom(
                com.google.protobuf.CodedInputStream input)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageLite.parseFrom(
                    DEFAULT_INSTANCE, input);
        }

        public static SendRequest parseFrom(
                com.google.protobuf.CodedInputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageLite.parseFrom(
                    DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(SendRequest prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static SendRequest getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static com.google.protobuf.Parser<SendRequest> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        /**
         * <code>string token = 1;</code>
         * @return The token.
         */
        @Override
        public String getToken() {
            return token_;
        }

        /**
         * <code>string token = 1;</code>
         * @param value The token to set.
         */
        private void setToken(
                String value) {
            value.getClass();

            token_ = value;
        }

        /**
         * <code>string token = 1;</code>
         * @return The bytes for token.
         */
        @Override
        public com.google.protobuf.ByteString
        getTokenBytes() {
            return com.google.protobuf.ByteString.copyFromUtf8(token_);
        }

        /**
         * <code>string token = 1;</code>
         * @param value The bytes for token to set.
         */
        private void setTokenBytes(
                com.google.protobuf.ByteString value) {
            checkByteStringIsUtf8(value);
            token_ = value.toStringUtf8();

        }

        /**
         * <code>string token = 1;</code>
         */
        private void clearToken() {

            token_ = getDefaultInstance().getToken();
        }

        /**
         * <code>string bulking = 2;</code>
         * @return The bulking.
         */
        @Override
        public String getBulking() {
            return bulking_;
        }

        /**
         * <code>string bulking = 2;</code>
         * @param value The bulking to set.
         */
        private void setBulking(
                String value) {
            value.getClass();

            bulking_ = value;
        }

        /**
         * <code>string bulking = 2;</code>
         * @return The bytes for bulking.
         */
        @Override
        public com.google.protobuf.ByteString
        getBulkingBytes() {
            return com.google.protobuf.ByteString.copyFromUtf8(bulking_);
        }

        /**
         * <code>string bulking = 2;</code>
         * @param value The bytes for bulking to set.
         */
        private void setBulkingBytes(
                com.google.protobuf.ByteString value) {
            checkByteStringIsUtf8(value);
            bulking_ = value.toStringUtf8();

        }

        /**
         * <code>string bulking = 2;</code>
         */
        private void clearBulking() {

            bulking_ = getDefaultInstance().getBulking();
        }

        /**
         * <code>bytes data = 3;</code>
         * @param value The data to set.
         */
        private void setData(com.google.protobuf.ByteString value) {
            value.getClass();

            data_ = value;
        }

        /**
         * <code>bytes data = 3;</code>
         */
        private void clearData() {

            data_ = getDefaultInstance().getData();
        }

        @Override
        @SuppressWarnings({"unchecked", "fallthrough"})
        protected final Object dynamicMethod(
                MethodToInvoke method,
                Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE: {
                    return new SendRequest();
                }
                case NEW_BUILDER: {
                    return new Builder();
                }
                case BUILD_MESSAGE_INFO: {
                    Object[] objects = new Object[]{
                            "token_",
                            "bulking_",
                            "data_",
                    };
                    String info =
                            "\u0000\u0003\u0000\u0000\u0001\u0003\u0003\u0000\u0000\u0000\u0001\u0208\u0002\u0208" +
                                    "\u0003\n";
                    return newMessageInfo(DEFAULT_INSTANCE, info, objects);
                }
                // fall through
                case GET_DEFAULT_INSTANCE: {
                    return DEFAULT_INSTANCE;
                }
                case GET_PARSER: {
                    com.google.protobuf.Parser<SendRequest> parser = PARSER;
                    if (parser == null) {
                        synchronized (SendRequest.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser =
                                        new DefaultInstanceBasedParser<SendRequest>(
                                                DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                }
                case GET_MEMOIZED_IS_INITIALIZED: {
                    return (byte) 1;
                }
                case SET_MEMOIZED_IS_INITIALIZED: {
                    return null;
                }
            }
            throw new UnsupportedOperationException();
        }

        /**
         * Protobuf type {@code SendRequest}
         */
        public static final class Builder extends
                com.google.protobuf.GeneratedMessageLite.Builder<
                        SendRequest, Builder> implements
                // @@protoc_insertion_point(builder_implements:SendRequest)
                SendRequestOrBuilder {
            // Construct using SendModule.SendRequest.newBuilder()
            private Builder() {
                super(DEFAULT_INSTANCE);
            }


            /**
             * <code>string token = 1;</code>
             * @return The token.
             */
            @Override
            public String getToken() {
                return instance.getToken();
            }

            /**
             * <code>string token = 1;</code>
             * @param value The token to set.
             * @return This builder for chaining.
             */
            public Builder setToken(
                    String value) {
                copyOnWrite();
                instance.setToken(value);
                return this;
            }

            /**
             * <code>string token = 1;</code>
             *
             * @return The bytes for token.
             */
            @Override
            public com.google.protobuf.ByteString
            getTokenBytes() {
                return instance.getTokenBytes();
            }

            /**
             * <code>string token = 1;</code>
             *
             * @param value The bytes for token to set.
             * @return This builder for chaining.
             */
            public Builder setTokenBytes(
                    com.google.protobuf.ByteString value) {
                copyOnWrite();
                instance.setTokenBytes(value);
                return this;
            }

            /**
             * <code>string token = 1;</code>
             *
             * @return This builder for chaining.
             */
            public Builder clearToken() {
                copyOnWrite();
                instance.clearToken();
                return this;
            }

            /**
             * <code>string bulking = 2;</code>
             *
             * @return The bulking.
             */
            @Override
            public String getBulking() {
                return instance.getBulking();
            }

            /**
             * <code>string bulking = 2;</code>
             * @param value The bulking to set.
             * @return This builder for chaining.
             */
            public Builder setBulking(
                    String value) {
                copyOnWrite();
                instance.setBulking(value);
                return this;
      }

      /**
       * <code>string bulking = 2;</code>
       * @return The bytes for bulking.
       */
      @Override
      public com.google.protobuf.ByteString
      getBulkingBytes() {
          return instance.getBulkingBytes();
      }

            /**
             * <code>string bulking = 2;</code>
             *
             * @param value The bytes for bulking to set.
             * @return This builder for chaining.
             */
            public Builder setBulkingBytes(
                    com.google.protobuf.ByteString value) {
                copyOnWrite();
                instance.setBulkingBytes(value);
                return this;
            }

            /**
             * <code>string bulking = 2;</code>
             *
             * @return This builder for chaining.
             */
            public Builder clearBulking() {
                copyOnWrite();
        instance.clearBulking();
        return this;
      }

      /**
       * <code>bytes data = 3;</code>
       * @return The data.
       */
      @Override
      public com.google.protobuf.ByteString getData() {
          return instance.getData();
      }
      /**
       * <code>bytes data = 3;</code>
       * @param value The data to set.
       * @return This builder for chaining.
       */
      public Builder setData(com.google.protobuf.ByteString value) {
          copyOnWrite();
          instance.setData(value);
        return this;
      }
      /**
       * <code>bytes data = 3;</code>
       * @return This builder for chaining.
       */
      public Builder clearData() {
          copyOnWrite();
          instance.clearData();
          return this;
      }

      // @@protoc_insertion_point(builder_scope:SendRequest)
    }
  }


  static {
  }

  // @@protoc_insertion_point(outer_class_scope)
}
