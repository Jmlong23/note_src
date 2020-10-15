---
title: 用openssl对代码进行签名和验证的例子跑通
toc: true
date: 2020-09-22 16:05:29
tags:
categories: c++安全学
---

参考例子：

源码：<https://gist.github.com/irbull/08339ddcd5686f509e9826964b17bb59>

源码解析：<https://eclipsesource.com/blogs/2016/09/07/tutorial-code-signing-and-verification-with-openssl/>

<!--more-->

# 一、vs2019项目属性配置

项目目录，把openssl的bin、lib、include目录拷贝进来

![](/images/20200922/1.png)

引入include目录../../include

![](/images/20200922/2.png)

引入lib目录../../lib

![](/images/20200922/3.png)

导入lib里面的libcrypto.lib文件名

![](/images/20200922/4.png)

这部要留意，如果显示“无法解析的外部符号 _BIO_new，函数 "void __cdecl Base64Decode(char const *,unsigned char * *,unsigned int *)" (?Base64Decode@@YAXPBDPAPAEPAI@Z) 中引用了该符号”的错误就是没有导入该文件。

运行结果图：

![](/images/20200922/0.png)

# 二、原理

## 代码签名和验证

代码签名和验证是对可执行文件或脚本进行数字签名的过程，以确保您执行的软件自签名以来没有被更改。代码签名有助于防止损坏的工件，进程崩溃（意外传送错误的东西），甚至恶意意图。

代码签名和验证的工作原理如下，作者还以代码为输入执行 *哈希函数*，从而产生 *摘要*。摘要使用作者的私钥进行进行加密，得到数字签名。然后，将代码，数字签名和哈希函数传递给验证者。验证者使用相同的哈希函数从代码生成摘要，然后使用公钥解密签名。如果两个摘要都匹配，则验证者可以确信代码未被篡改。

## 生成密钥

<https://cloud.tencent.com/developer/article/1622834>

```
生成私钥
openssl.exe genrsa -out rsa.private 1024


-----BEGIN RSA PRIVATE KEY-----
MIICXQIBAAKBgQC6gR460tNZV9+rjeC46Jlba6vcwgPHlh0kWdebg+/lkR5uYs3W
esBmlox7W1mFfWbv93X2WTVMdVbo/MteKKR2A4Hs7+kg+BFriXhdyCazBhP5ts9f
vSQUOjGjq/hI6q6oIKOfXNez1VQ5SfNKGO6grFvQq2pmrO/SyQOh2BK7XwIDAQAB
AoGAbtFbwf4VYOfq2kfSOGzU+tJOazzI/CXBKSFNEHXj7Jc+6r5AqmmDuzFHReDw
n3X03S8/42H8XnL2cjgLKuahWQzvyCltBRYsWU/lFGg2baGsnEEeuq5OgTZZ0z5W
NKAL31u8XBFlv5sCuFGu3e71ggnenkMl4GMdjx3mnMdOLpkCQQDzQxKrOhMztN5W
rDzTav85+RjGFQAtSw41lwACp0+lzj+Q2rWIBl/Lt4h1Ov9MXVAaEHtyYS6DyIvd
vNCIvOB7AkEAxEU1hXM7r3xuuDOzbIHCe6giveQZntDNcwIkzrg9Nc48MH1BBJeu
MGWVvJQp6rab/Ih73Cnc0iwsVKClw5JFbQJBALf6CTuAj5cyelk4qEQJDwAsWUUu
CtU2h4eWLQbUvNMcWkzWbCZ1E6xBoS1wMjbx96vOvV4zC3fVi5pmu5w+pNMCQQCN
81xFO1V0kzNkyAfBt4uYcb9GxX1+LpgY0PkcBYZHrvQ7QavPjYBvAlmsvSAf4Jiq
qW+jiSdrXoVlY5bf/p91AkAwHhRLuUxplgQcyRkEXzHud5znBiOKdpztI+ngTiWO
FiaC6rRtqJYJnPphqtHp4jns7nOYmy8mzJDKGmwsdCXj
-----END RSA PRIVATE KEY-----

生成公钥
openssl.exe rsa -in rsa.private -out rsa.public -pubout -outform PEM

-----BEGIN PUBLIC KEY-----
MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC6gR460tNZV9+rjeC46Jlba6vc
wgPHlh0kWdebg+/lkR5uYs3WesBmlox7W1mFfWbv93X2WTVMdVbo/MteKKR2A4Hs
7+kg+BFriXhdyCazBhP5ts9fvSQUOjGjq/hI6q6oIKOfXNez1VQ5SfNKGO6grFvQ
q2pmrO/SyQOh2BK7XwIDAQAB
-----END PUBLIC KEY-----

```

## 数字签名

数字签名在发送方，分两步：

（1）从内容算摘要（哈希算法）

（2）从摘要明文到摘要密文，也称数字签名（发送方私钥+加密算法）

## 数字验证

数字签名验证在接收方，分两步：

（1）从摘要密文（数字签名）到摘要明文（发送方公钥+解密算法）

（2）从收到的内容当场计算摘要（哈希算法），与（1）的结果比对是否一致

```
#include <iostream>
#include <openssl/aes.h>
#include <openssl/evp.h>
#include <openssl/rsa.h>
#include <openssl/pem.h>
#include <openssl/ssl.h>
#include <openssl/bio.h>
#include <openssl/err.h>
#include <assert.h>

std::string privateKey = "-----BEGIN RSA PRIVATE KEY-----\n"\
"MIIEowIBAAKCAQEAy8Dbv8prpJ/0kKhlGeJYozo2t60EG8L0561g13R29LvMR5hy\n"\
"vGZlGJpmn65+A4xHXInJYiPuKzrKUnApeLZ+vw1HocOAZtWK0z3r26uA8kQYOKX9\n"\
"Qt/DbCdvsF9wF8gRK0ptx9M6R13NvBxvVQApfc9jB9nTzphOgM4JiEYvlV8FLhg9\n"\
"yZovMYd6Wwf3aoXK891VQxTr/kQYoq1Yp+68i6T4nNq7NWC+UNVjQHxNQMQMzU6l\n"\
"WCX8zyg3yH88OAQkUXIXKfQ+NkvYQ1cxaMoVPpY72+eVthKzpMeyHkBn7ciumk5q\n"\
"gLTEJAfWZpe4f4eFZj/Rc8Y8Jj2IS5kVPjUywQIDAQABAoIBADhg1u1Mv1hAAlX8\n"\
"omz1Gn2f4AAW2aos2cM5UDCNw1SYmj+9SRIkaxjRsE/C4o9sw1oxrg1/z6kajV0e\n"\
"N/t008FdlVKHXAIYWF93JMoVvIpMmT8jft6AN/y3NMpivgt2inmmEJZYNioFJKZG\n"\
"X+/vKYvsVISZm2fw8NfnKvAQK55yu+GRWBZGOeS9K+LbYvOwcrjKhHz66m4bedKd\n"\
"gVAix6NE5iwmjNXktSQlJMCjbtdNXg/xo1/G4kG2p/MO1HLcKfe1N5FgBiXj3Qjl\n"\
"vgvjJZkh1as2KTgaPOBqZaP03738VnYg23ISyvfT/teArVGtxrmFP7939EvJFKpF\n"\
"1wTxuDkCgYEA7t0DR37zt+dEJy+5vm7zSmN97VenwQJFWMiulkHGa0yU3lLasxxu\n"\
"m0oUtndIjenIvSx6t3Y+agK2F3EPbb0AZ5wZ1p1IXs4vktgeQwSSBdqcM8LZFDvZ\n"\
"uPboQnJoRdIkd62XnP5ekIEIBAfOp8v2wFpSfE7nNH2u4CpAXNSF9HsCgYEA2l8D\n"\
"JrDE5m9Kkn+J4l+AdGfeBL1igPF3DnuPoV67BpgiaAgI4h25UJzXiDKKoa706S0D\n"\
"4XB74zOLX11MaGPMIdhlG+SgeQfNoC5lE4ZWXNyESJH1SVgRGT9nBC2vtL6bxCVV\n"\
"WBkTeC5D6c/QXcai6yw6OYyNNdp0uznKURe1xvMCgYBVYYcEjWqMuAvyferFGV+5\n"\
"nWqr5gM+yJMFM2bEqupD/HHSLoeiMm2O8KIKvwSeRYzNohKTdZ7FwgZYxr8fGMoG\n"\
"PxQ1VK9DxCvZL4tRpVaU5Rmknud9hg9DQG6xIbgIDR+f79sb8QjYWmcFGc1SyWOA\n"\
"SkjlykZ2yt4xnqi3BfiD9QKBgGqLgRYXmXp1QoVIBRaWUi55nzHg1XbkWZqPXvz1\n"\
"I3uMLv1jLjJlHk3euKqTPmC05HoApKwSHeA0/gOBmg404xyAYJTDcCidTg6hlF96\n"\
"ZBja3xApZuxqM62F6dV4FQqzFX0WWhWp5n301N33r0qR6FumMKJzmVJ1TA8tmzEF\n"\
"yINRAoGBAJqioYs8rK6eXzA8ywYLjqTLu/yQSLBn/4ta36K8DyCoLNlNxSuox+A5\n"\
"w6z2vEfRVQDq4Hm4vBzjdi3QfYLNkTiTqLcvgWZ+eX44ogXtdTDO7c+GeMKWz4XX\n"\
"uJSUVL5+CVjKLjZEJ6Qc2WZLl94xSwL71E41H4YciVnSCQxVc4Jw\n"\
"-----END RSA PRIVATE KEY-----\n\0";

std::string publicKey = "-----BEGIN PUBLIC KEY-----\n"\
"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAy8Dbv8prpJ/0kKhlGeJY\n"\
"ozo2t60EG8L0561g13R29LvMR5hyvGZlGJpmn65+A4xHXInJYiPuKzrKUnApeLZ+\n"\
"vw1HocOAZtWK0z3r26uA8kQYOKX9Qt/DbCdvsF9wF8gRK0ptx9M6R13NvBxvVQAp\n"\
"fc9jB9nTzphOgM4JiEYvlV8FLhg9yZovMYd6Wwf3aoXK891VQxTr/kQYoq1Yp+68\n"\
"i6T4nNq7NWC+UNVjQHxNQMQMzU6lWCX8zyg3yH88OAQkUXIXKfQ+NkvYQ1cxaMoV\n"\
"PpY72+eVthKzpMeyHkBn7ciumk5qgLTEJAfWZpe4f4eFZj/Rc8Y8Jj2IS5kVPjUy\n"\
"wQIDAQAB\n"\
"-----END PUBLIC KEY-----\n";

RSA* createPrivateRSA(std::string key) {
    RSA* rsa = NULL;
    const char* c_string = key.c_str();
    BIO* keybio = BIO_new_mem_buf((void*)c_string, -1);
    if (keybio == NULL) {
        return 0;
    }
    rsa = PEM_read_bio_RSAPrivateKey(keybio, &rsa, NULL, NULL);
    return rsa;
}

RSA* createPublicRSA(std::string key) {
    RSA* rsa = NULL;
    BIO* keybio;
    const char* c_string = key.c_str();
    keybio = BIO_new_mem_buf((void*)c_string, -1);
    if (keybio == NULL) {
        return 0;
    }
    rsa = PEM_read_bio_RSA_PUBKEY(keybio, &rsa, NULL, NULL);
    return rsa;
}
//With an RSA object and plaintext you can create the digest and binary digital signature:
bool RSASign(RSA* rsa,
    const unsigned char* Msg,
    size_t MsgLen,
    unsigned char** EncMsg,
    size_t* MsgLenEnc) {
    EVP_MD_CTX* m_RSASignCtx = EVP_MD_CTX_create();
    EVP_PKEY* priKey = EVP_PKEY_new();
    EVP_PKEY_assign_RSA(priKey, rsa);
    if (EVP_DigestSignInit(m_RSASignCtx, NULL, EVP_sha256(), NULL, priKey) <= 0) {
        return false;
    }
    if (EVP_DigestSignUpdate(m_RSASignCtx, Msg, MsgLen) <= 0) {
        return false;
    }
    if (EVP_DigestSignFinal(m_RSASignCtx, NULL, MsgLenEnc) <= 0) {
        return false;
    }
    *EncMsg = (unsigned char*)malloc(*MsgLenEnc);
    if (EVP_DigestSignFinal(m_RSASignCtx, *EncMsg, MsgLenEnc) <= 0) {
        return false;
    }
    EVP_MD_CTX_free(m_RSASignCtx);
    return true;
}
//with the RSA object, original message and binary encoded signature, you can verify that the signature matches the plain text.
bool RSAVerifySignature(RSA* rsa,
    unsigned char* MsgHash,
    size_t MsgHashLen,
    const char* Msg,
    size_t MsgLen,
    bool* Authentic) {
    *Authentic = false;
    EVP_PKEY* pubKey = EVP_PKEY_new();
    EVP_PKEY_assign_RSA(pubKey, rsa);
    EVP_MD_CTX* m_RSAVerifyCtx = EVP_MD_CTX_create();

    if (EVP_DigestVerifyInit(m_RSAVerifyCtx, NULL, EVP_sha256(), NULL, pubKey) <= 0) {
        return false;
    }
    if (EVP_DigestVerifyUpdate(m_RSAVerifyCtx, Msg, MsgLen) <= 0) {
        return false;
    }
    int AuthStatus = EVP_DigestVerifyFinal(m_RSAVerifyCtx, MsgHash, MsgHashLen);
    if (AuthStatus == 1) {
        *Authentic = true;
        EVP_MD_CTX_free(m_RSAVerifyCtx);
        return true;
    }
    else if (AuthStatus == 0) {
        *Authentic = false;
        EVP_MD_CTX_free(m_RSAVerifyCtx);
        return true;
    }
    else {
        *Authentic = false;
        EVP_MD_CTX_free(m_RSAVerifyCtx);
        return false;
    }
}

void Base64Encode(const unsigned char* buffer,
    size_t length,
    char** base64Text) {
    BIO* bio, * b64;
    BUF_MEM* bufferPtr;

    b64 = BIO_new(BIO_f_base64());
    bio = BIO_new(BIO_s_mem());
    bio = BIO_push(b64, bio);

    BIO_write(bio, buffer, length);
    BIO_flush(bio);
    BIO_get_mem_ptr(bio, &bufferPtr);
    BIO_set_close(bio, BIO_NOCLOSE);
    BIO_free_all(bio);

    *base64Text = (*bufferPtr).data;
}

size_t calcDecodeLength(const char* b64input) {
    size_t len = strlen(b64input), padding = 0;

    if (b64input[len - 1] == '=' && b64input[len - 2] == '=') //last two chars are =
        padding = 2;
    else if (b64input[len - 1] == '=') //last char is =
        padding = 1;
    return (len * 3) / 4 - padding;
}

void Base64Decode(const char* b64message, unsigned char** buffer, size_t* length) {
    BIO* bio, * b64;

    int decodeLen = calcDecodeLength(b64message);
    *buffer = (unsigned char*)malloc(decodeLen + 1);
    (*buffer)[decodeLen] = '\0';

    bio = BIO_new_mem_buf(b64message, -1);
    b64 = BIO_new(BIO_f_base64());
    bio = BIO_push(b64, bio);

    *length = BIO_read(bio, *buffer, strlen(b64message));
    BIO_free_all(bio);
}

char* signMessage(std::string privateKey, std::string plainText) {
    //用私钥初始化一个rsa对象
    RSA* privateRSA = createPrivateRSA(privateKey);
    unsigned char* encMessage;
    char* base64Text;
    size_t encMessageLength;
    RSASign(privateRSA, (unsigned char*)plainText.c_str(), plainText.length(), &encMessage, &encMessageLength);
    Base64Encode(encMessage, encMessageLength, &base64Text);
    free(encMessage);
    return base64Text;
}

bool verifySignature(std::string publicKey, std::string plainText, char* signatureBase64) {
    RSA* publicRSA = createPublicRSA(publicKey);
    unsigned char* encMessage;
    size_t encMessageLength;
    bool authentic;
    Base64Decode(signatureBase64, &encMessage, &encMessageLength);
    bool result = RSAVerifySignature(publicRSA, encMessage, encMessageLength, plainText.c_str(), plainText.length(), &authentic);
    return result & authentic;
}

int main() {
    std::string plainText = "My secret message.\n";
    //得到用base64表示的数字签名
    char* signature = signMessage(privateKey, plainText);
    bool authentic = verifySignature(publicKey, "My secret message.\n", signature);
    if (authentic) {
        std::cout << "Authentic" << std::endl;
    }
    else {
        std::cout << "Not Authentic" << std::endl;
    }
}
```

