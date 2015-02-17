// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: message.proto

package com.netty4.erlproto;

public final class MessageBuf {
  private MessageBuf() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  public interface MessageOrBuilder
      extends com.google.protobuf.MessageOrBuilder {

    // required .proto.Header header = 1;
    /**
     * <code>required .proto.Header header = 1;</code>
     *
     * <pre>
     *头消息 
     * </pre>
     */
    boolean hasHeader();
    /**
     * <code>required .proto.Header header = 1;</code>
     *
     * <pre>
     *头消息 
     * </pre>
     */
    com.netty4.erlproto.HeaderBuf.Header getHeader();
    /**
     * <code>required .proto.Header header = 1;</code>
     *
     * <pre>
     *头消息 
     * </pre>
     */
    com.netty4.erlproto.HeaderBuf.HeaderOrBuilder getHeaderOrBuilder();

    // optional bytes body = 2;
    /**
     * <code>optional bytes body = 2;</code>
     *
     * <pre>
     *内容  
     * </pre>
     */
    boolean hasBody();
    /**
     * <code>optional bytes body = 2;</code>
     *
     * <pre>
     *内容  
     * </pre>
     */
    com.google.protobuf.ByteString getBody();
  }
  /**
   * Protobuf type {@code proto.Message}
   */
  public static final class Message extends
      com.google.protobuf.GeneratedMessage
      implements MessageOrBuilder {
    // Use Message.newBuilder() to construct.
    private Message(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
      super(builder);
      this.unknownFields = builder.getUnknownFields();
    }
    private Message(boolean noInit) { this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance(); }

    private static final Message defaultInstance;
    public static Message getDefaultInstance() {
      return defaultInstance;
    }

    public Message getDefaultInstanceForType() {
      return defaultInstance;
    }

    private final com.google.protobuf.UnknownFieldSet unknownFields;
    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
      return this.unknownFields;
    }
    private Message(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      initFields();
      int mutable_bitField0_ = 0;
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
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
            case 10: {
              com.netty4.erlproto.HeaderBuf.Header.Builder subBuilder = null;
              if (((bitField0_ & 0x00000001) == 0x00000001)) {
                subBuilder = header_.toBuilder();
              }
              header_ = input.readMessage(com.netty4.erlproto.HeaderBuf.Header.PARSER, extensionRegistry);
              if (subBuilder != null) {
                subBuilder.mergeFrom(header_);
                header_ = subBuilder.buildPartial();
              }
              bitField0_ |= 0x00000001;
              break;
            }
            case 18: {
              bitField0_ |= 0x00000002;
              body_ = input.readBytes();
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e.getMessage()).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.netty4.erlproto.MessageBuf.internal_static_proto_Message_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.netty4.erlproto.MessageBuf.internal_static_proto_Message_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.netty4.erlproto.MessageBuf.Message.class, com.netty4.erlproto.MessageBuf.Message.Builder.class);
    }

    public static com.google.protobuf.Parser<Message> PARSER =
        new com.google.protobuf.AbstractParser<Message>() {
      public Message parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new Message(input, extensionRegistry);
      }
    };

    @java.lang.Override
    public com.google.protobuf.Parser<Message> getParserForType() {
      return PARSER;
    }

    private int bitField0_;
    // required .proto.Header header = 1;
    public static final int HEADER_FIELD_NUMBER = 1;
    private com.netty4.erlproto.HeaderBuf.Header header_;
    /**
     * <code>required .proto.Header header = 1;</code>
     *
     * <pre>
     *头消息 
     * </pre>
     */
    public boolean hasHeader() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    /**
     * <code>required .proto.Header header = 1;</code>
     *
     * <pre>
     *头消息 
     * </pre>
     */
    public com.netty4.erlproto.HeaderBuf.Header getHeader() {
      return header_;
    }
    /**
     * <code>required .proto.Header header = 1;</code>
     *
     * <pre>
     *头消息 
     * </pre>
     */
    public com.netty4.erlproto.HeaderBuf.HeaderOrBuilder getHeaderOrBuilder() {
      return header_;
    }

    // optional bytes body = 2;
    public static final int BODY_FIELD_NUMBER = 2;
    private com.google.protobuf.ByteString body_;
    /**
     * <code>optional bytes body = 2;</code>
     *
     * <pre>
     *内容  
     * </pre>
     */
    public boolean hasBody() {
      return ((bitField0_ & 0x00000002) == 0x00000002);
    }
    /**
     * <code>optional bytes body = 2;</code>
     *
     * <pre>
     *内容  
     * </pre>
     */
    public com.google.protobuf.ByteString getBody() {
      return body_;
    }

    private void initFields() {
      header_ = com.netty4.erlproto.HeaderBuf.Header.getDefaultInstance();
      body_ = com.google.protobuf.ByteString.EMPTY;
    }
    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized != -1) return isInitialized == 1;

      if (!hasHeader()) {
        memoizedIsInitialized = 0;
        return false;
      }
      if (!getHeader().isInitialized()) {
        memoizedIsInitialized = 0;
        return false;
      }
      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        output.writeMessage(1, header_);
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        output.writeBytes(2, body_);
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;

      size = 0;
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(1, header_);
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(2, body_);
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;
    @java.lang.Override
    protected java.lang.Object writeReplace()
        throws java.io.ObjectStreamException {
      return super.writeReplace();
    }

    public static com.netty4.erlproto.MessageBuf.Message parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.netty4.erlproto.MessageBuf.Message parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.netty4.erlproto.MessageBuf.Message parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.netty4.erlproto.MessageBuf.Message parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.netty4.erlproto.MessageBuf.Message parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static com.netty4.erlproto.MessageBuf.Message parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }
    public static com.netty4.erlproto.MessageBuf.Message parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input);
    }
    public static com.netty4.erlproto.MessageBuf.Message parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input, extensionRegistry);
    }
    public static com.netty4.erlproto.MessageBuf.Message parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static com.netty4.erlproto.MessageBuf.Message parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }

    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(com.netty4.erlproto.MessageBuf.Message prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code proto.Message}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder>
       implements com.netty4.erlproto.MessageBuf.MessageOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.netty4.erlproto.MessageBuf.internal_static_proto_Message_descriptor;
      }

      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.netty4.erlproto.MessageBuf.internal_static_proto_Message_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.netty4.erlproto.MessageBuf.Message.class, com.netty4.erlproto.MessageBuf.Message.Builder.class);
      }

      // Construct using com.netty4.erlproto.MessageBuf.Message.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessage.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
          getHeaderFieldBuilder();
        }
      }
      private static Builder create() {
        return new Builder();
      }

      public Builder clear() {
        super.clear();
        if (headerBuilder_ == null) {
          header_ = com.netty4.erlproto.HeaderBuf.Header.getDefaultInstance();
        } else {
          headerBuilder_.clear();
        }
        bitField0_ = (bitField0_ & ~0x00000001);
        body_ = com.google.protobuf.ByteString.EMPTY;
        bitField0_ = (bitField0_ & ~0x00000002);
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(buildPartial());
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.netty4.erlproto.MessageBuf.internal_static_proto_Message_descriptor;
      }

      public com.netty4.erlproto.MessageBuf.Message getDefaultInstanceForType() {
        return com.netty4.erlproto.MessageBuf.Message.getDefaultInstance();
      }

      public com.netty4.erlproto.MessageBuf.Message build() {
        com.netty4.erlproto.MessageBuf.Message result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public com.netty4.erlproto.MessageBuf.Message buildPartial() {
        com.netty4.erlproto.MessageBuf.Message result = new com.netty4.erlproto.MessageBuf.Message(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
          to_bitField0_ |= 0x00000001;
        }
        if (headerBuilder_ == null) {
          result.header_ = header_;
        } else {
          result.header_ = headerBuilder_.build();
        }
        if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
          to_bitField0_ |= 0x00000002;
        }
        result.body_ = body_;
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.netty4.erlproto.MessageBuf.Message) {
          return mergeFrom((com.netty4.erlproto.MessageBuf.Message)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.netty4.erlproto.MessageBuf.Message other) {
        if (other == com.netty4.erlproto.MessageBuf.Message.getDefaultInstance()) return this;
        if (other.hasHeader()) {
          mergeHeader(other.getHeader());
        }
        if (other.hasBody()) {
          setBody(other.getBody());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public final boolean isInitialized() {
        if (!hasHeader()) {
          
          return false;
        }
        if (!getHeader().isInitialized()) {
          
          return false;
        }
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.netty4.erlproto.MessageBuf.Message parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (com.netty4.erlproto.MessageBuf.Message) e.getUnfinishedMessage();
          throw e;
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      // required .proto.Header header = 1;
      private com.netty4.erlproto.HeaderBuf.Header header_ = com.netty4.erlproto.HeaderBuf.Header.getDefaultInstance();
      private com.google.protobuf.SingleFieldBuilder<
          com.netty4.erlproto.HeaderBuf.Header, com.netty4.erlproto.HeaderBuf.Header.Builder, com.netty4.erlproto.HeaderBuf.HeaderOrBuilder> headerBuilder_;
      /**
       * <code>required .proto.Header header = 1;</code>
       *
       * <pre>
       *头消息 
       * </pre>
       */
      public boolean hasHeader() {
        return ((bitField0_ & 0x00000001) == 0x00000001);
      }
      /**
       * <code>required .proto.Header header = 1;</code>
       *
       * <pre>
       *头消息 
       * </pre>
       */
      public com.netty4.erlproto.HeaderBuf.Header getHeader() {
        if (headerBuilder_ == null) {
          return header_;
        } else {
          return headerBuilder_.getMessage();
        }
      }
      /**
       * <code>required .proto.Header header = 1;</code>
       *
       * <pre>
       *头消息 
       * </pre>
       */
      public Builder setHeader(com.netty4.erlproto.HeaderBuf.Header value) {
        if (headerBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          header_ = value;
          onChanged();
        } else {
          headerBuilder_.setMessage(value);
        }
        bitField0_ |= 0x00000001;
        return this;
      }
      /**
       * <code>required .proto.Header header = 1;</code>
       *
       * <pre>
       *头消息 
       * </pre>
       */
      public Builder setHeader(
          com.netty4.erlproto.HeaderBuf.Header.Builder builderForValue) {
        if (headerBuilder_ == null) {
          header_ = builderForValue.build();
          onChanged();
        } else {
          headerBuilder_.setMessage(builderForValue.build());
        }
        bitField0_ |= 0x00000001;
        return this;
      }
      /**
       * <code>required .proto.Header header = 1;</code>
       *
       * <pre>
       *头消息 
       * </pre>
       */
      public Builder mergeHeader(com.netty4.erlproto.HeaderBuf.Header value) {
        if (headerBuilder_ == null) {
          if (((bitField0_ & 0x00000001) == 0x00000001) &&
              header_ != com.netty4.erlproto.HeaderBuf.Header.getDefaultInstance()) {
            header_ =
              com.netty4.erlproto.HeaderBuf.Header.newBuilder(header_).mergeFrom(value).buildPartial();
          } else {
            header_ = value;
          }
          onChanged();
        } else {
          headerBuilder_.mergeFrom(value);
        }
        bitField0_ |= 0x00000001;
        return this;
      }
      /**
       * <code>required .proto.Header header = 1;</code>
       *
       * <pre>
       *头消息 
       * </pre>
       */
      public Builder clearHeader() {
        if (headerBuilder_ == null) {
          header_ = com.netty4.erlproto.HeaderBuf.Header.getDefaultInstance();
          onChanged();
        } else {
          headerBuilder_.clear();
        }
        bitField0_ = (bitField0_ & ~0x00000001);
        return this;
      }
      /**
       * <code>required .proto.Header header = 1;</code>
       *
       * <pre>
       *头消息 
       * </pre>
       */
      public com.netty4.erlproto.HeaderBuf.Header.Builder getHeaderBuilder() {
        bitField0_ |= 0x00000001;
        onChanged();
        return getHeaderFieldBuilder().getBuilder();
      }
      /**
       * <code>required .proto.Header header = 1;</code>
       *
       * <pre>
       *头消息 
       * </pre>
       */
      public com.netty4.erlproto.HeaderBuf.HeaderOrBuilder getHeaderOrBuilder() {
        if (headerBuilder_ != null) {
          return headerBuilder_.getMessageOrBuilder();
        } else {
          return header_;
        }
      }
      /**
       * <code>required .proto.Header header = 1;</code>
       *
       * <pre>
       *头消息 
       * </pre>
       */
      private com.google.protobuf.SingleFieldBuilder<
          com.netty4.erlproto.HeaderBuf.Header, com.netty4.erlproto.HeaderBuf.Header.Builder, com.netty4.erlproto.HeaderBuf.HeaderOrBuilder> 
          getHeaderFieldBuilder() {
        if (headerBuilder_ == null) {
          headerBuilder_ = new com.google.protobuf.SingleFieldBuilder<
              com.netty4.erlproto.HeaderBuf.Header, com.netty4.erlproto.HeaderBuf.Header.Builder, com.netty4.erlproto.HeaderBuf.HeaderOrBuilder>(
                  header_,
                  getParentForChildren(),
                  isClean());
          header_ = null;
        }
        return headerBuilder_;
      }

      // optional bytes body = 2;
      private com.google.protobuf.ByteString body_ = com.google.protobuf.ByteString.EMPTY;
      /**
       * <code>optional bytes body = 2;</code>
       *
       * <pre>
       *内容  
       * </pre>
       */
      public boolean hasBody() {
        return ((bitField0_ & 0x00000002) == 0x00000002);
      }
      /**
       * <code>optional bytes body = 2;</code>
       *
       * <pre>
       *内容  
       * </pre>
       */
      public com.google.protobuf.ByteString getBody() {
        return body_;
      }
      /**
       * <code>optional bytes body = 2;</code>
       *
       * <pre>
       *内容  
       * </pre>
       */
      public Builder setBody(com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000002;
        body_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional bytes body = 2;</code>
       *
       * <pre>
       *内容  
       * </pre>
       */
      public Builder clearBody() {
        bitField0_ = (bitField0_ & ~0x00000002);
        body_ = getDefaultInstance().getBody();
        onChanged();
        return this;
      }

      // @@protoc_insertion_point(builder_scope:proto.Message)
    }

    static {
      defaultInstance = new Message(true);
      defaultInstance.initFields();
    }

    // @@protoc_insertion_point(class_scope:proto.Message)
  }

  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_proto_Message_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_proto_Message_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\rmessage.proto\022\005proto\032\014header.proto\"6\n\007" +
      "Message\022\035\n\006header\030\001 \002(\0132\r.proto.Header\022\014" +
      "\n\004body\030\002 \001(\014B!\n\023com.netty4.erlprotoB\nMes" +
      "sageBuf"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
      new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
        public com.google.protobuf.ExtensionRegistry assignDescriptors(
            com.google.protobuf.Descriptors.FileDescriptor root) {
          descriptor = root;
          internal_static_proto_Message_descriptor =
            getDescriptor().getMessageTypes().get(0);
          internal_static_proto_Message_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_proto_Message_descriptor,
              new java.lang.String[] { "Header", "Body", });
          return null;
        }
      };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          com.netty4.erlproto.HeaderBuf.getDescriptor(),
        }, assigner);
  }

  // @@protoc_insertion_point(outer_class_scope)
}
