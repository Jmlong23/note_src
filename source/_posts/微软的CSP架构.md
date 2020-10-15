---
title: 微软的CSP架构
toc: true
date: 2020-09-30 09:23:52
tags:
categories: c++安全学
---

# 问题

## 无法解析的外部符号 

__imp__CertFreeCertificateContext

<https://blog.csdn.net/diaoxuesong/article/details/78664663>

在跑用csp获取证书那个例子时，遇到错误，只要在 **项目属性 - 链接器 - 输入 - 附加依赖项** 中加入： **Crypt32.lib** 即可。

# CSP入门简介

<https://blog.csdn.net/liuhuiyi/article/details/7778742>

<!--more-->

## CryptAcquireContext函数

<https://blog.csdn.net/Qiplus/article/details/8086100?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-6.edu_weight&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-6.edu_weight>



# Csp第一个例子

<https://blog.csdn.net/liuhuiyi/article/details/7778968>

看代码一



# CSP第二个例子

<https://blog.csdn.net/liuhuiyi/article/details/7787435>

运行结果

```
打开句柄成功
获得签名密钥成功。
we get the length of the public key.
we get the memory.
export the public key.
CreateHash succeed.
HashData succeed.
 Get the length of signature.
get the memory.
signature succeed.
Signature:
8a7595ada1c9125a86b4d01687a35768bd22728519302bfdd37559be6e2e1a64b5ff2d33803debb17d43cf3c6e50f21bbebe8ae2dfb7eab3c27d6c1b7142cd1ca4b28527021bd9849d63f6421a2e7a0f3df12371bd03ba18d9f608b3a5f4d21384b634a77f621b79991de2a8c324c7d968abf5a076bace2fd09c3f4e3708a1ae
OK.
Import the key.
创建哈希对象成功
数据哈希完成.
验证签名成功。
```

看代码二

https://www.cnblogs.com/whbCNW/tag/CSP/



# 探索用CSP获取商店里面的证书来进行签名和验证



<https://www.sysadmins.lv/retired-msft-blogs/alejacma/how-to-sign-and-verify-with-cryptoapi-and-a-user-certificate.aspx>

<https://zhuanlan.zhihu.com/p/38105475>

-

看代码4

# 用CSP获取系统里面的证书并且显示

<https://www.coder.work/article/982837>

```
#include <stdio.h>
#include <conio.h>
#include <windows.h>
#include <wincrypt.h>
#include <string>
using namespace std;

#define CERT_PERSONAL_STORE_NAME  L"My"
#define CERT_OTHER_PEOPLE_STORE_NAME L"AddressBook"
#define MY_TYPE  (PKCS_7_ASN_ENCODING | X509_ASN_ENCODING)
#define BUFSIZE 1024

void printfSerial(unsigned long count,unsigned char* str) {
    for (int i = 0; i < count; i++) {
        printf("%2.2x", str[count - i -1]);
    }
    printf("\n");
}

void printfPublicKey(unsigned long count, unsigned char* str) {
    for (int i = 0; i < count; i++) {
        printf("%2.2x", str[i]);
    }
    printf("\n");
}
void printfSubject(unsigned long count, unsigned char* str) {
    for (int i = 0; i < count; i++) {
        printf("%c", str[i]);
    }
    printf("\n");
}
void main() {

    HCERTSTORE hCertStore = NULL;
    PCCERT_CONTEXT pCertContext = NULL;
    PBYTE pbPKEY = NULL;
    DWORD iPKEYSize;
    const char* str = "360200265999946.p.3602";
    DWORD dwNum = MultiByteToWideChar(CP_ACP, 0, str, -1, NULL, 0); // 转换成 宽字节大小9 (8+1)
    wchar_t* SignerName = new WCHAR[dwNum]; // 按宽字节 new 内存
    memset(SignerName, 0, dwNum * sizeof(wchar_t)); // 全置0  可以用这句 memset(wch, 0, sizeof(wch));
    // memset( the_array, '\0', sizeof(the_array) ); 这是将一个数组的所以分量设置成零的很便捷的方法

    MultiByteToWideChar(CP_ACP, 0, str, -1, SignerName, dwNum * sizeof(wchar_t)); //s转换到宽字节wch
    
    hCertStore = CertOpenStore(
        CERT_STORE_PROV_SYSTEM,
        0,
        NULL,
        CERT_SYSTEM_STORE_CURRENT_USER,
        CERT_PERSONAL_STORE_NAME
    );
    pCertContext = CertFindCertificateInStore(
        hCertStore,
        MY_TYPE,
        0,
        CERT_FIND_SUBJECT_STR,
        SignerName,
        pCertContext
    );

    //printf("version:%ld", pCertContext->pCertInfo->dwVersion + 1);
    printf("SerialNumber:");
    printfSerial(pCertContext->pCertInfo->SerialNumber.cbData, pCertContext->pCertInfo->SerialNumber.pbData);
    printf("SignatureAlgorithm:%s\n", pCertContext->pCertInfo->SignatureAlgorithm.pszObjId);
    printf("Subject:\n");
    printfSubject(pCertContext->pCertInfo->Subject.cbData,pCertContext->pCertInfo->Subject.pbData);
    printf("PublicKey:");
    printfPublicKey(pCertContext->pCertInfo->SubjectPublicKeyInfo.PublicKey.cbData, pCertContext->pCertInfo->SubjectPublicKeyInfo.PublicKey.pbData);
}
```



# 读取usbkey中的密钥容器

<https://www.geek-share.com/detail/2730619280.html>

<https://blog.csdn.net/xuebing1995/article/details/79388971>

看代码3

```
#include <stdio.h>
#include <direct.h>
#include <string>
#include <vector>
#include <Windows.h>
using namespace std;

void main() {


	DWORD dwIndex = 9;
	DWORD dwType = 0;
	DWORD dwNameLen = 0;
	HCRYPTPROV hProv = NULL;
	DWORD dwErr;

	while (CryptEnumProviders(dwIndex, NULL, 0, &dwType, NULL, &dwNameLen))
	{
		DWORD dwItem = 0;
		TCHAR* pName = new TCHAR[dwNameLen + 1];
		if (CryptEnumProviders(dwIndex++, NULL, 0, &dwType, pName, &dwNameLen))
		{
			if (!CryptAcquireContext(&hProv, NULL, pName, dwType, 0))
			{
				dwErr = GetLastError();
			}
			else {
				int num = WideCharToMultiByte(CP_OEMCP, NULL, (LPWSTR)pName, -1, NULL, 0, NULL, FALSE);
				char* pchar = new char[num];
				WideCharToMultiByte(CP_OEMCP, NULL, (LPWSTR)pName, -1, pchar, num, NULL, FALSE);
				printf("%s\n", pchar);
			}

		}	
	}
	printf("end");
}
```

代码4

```
// Includes
#include <stdio.h>
#include <conio.h>
#include <windows.h>
#include <wincrypt.h>

// Defines
#define CERT_PERSONAL_STORE_NAME  L"My"
#define CERT_OTHER_PEOPLE_STORE_NAME L"AddressBook"
#define MY_TYPE  (PKCS_7_ASN_ENCODING | X509_ASN_ENCODING)
#define BUFSIZE 1024

// Local functions
void Sign(wchar_t* SignerName, wchar_t* DataFileName, wchar_t* SignatureFileName);
void Verify(wchar_t* SignerName, wchar_t* SignatureFileName, wchar_t* DataFileName);

// ShowUsageAndExit
void ShowUsageAndExit()
{
    wprintf(L"Usage:\n");
    wprintf(L"   - To sign:   SignVerify s signer_name data_file signature_file\n");
    wprintf(L"   - To verify: SignVerify v signer_name data_file signature_file\n");
    wprintf(L"\n<< Press any key to continue >>\n");
    _getch();
    exit(1);
}
// End of ShowUsageAndExit

// CheckError
void CheckError(BOOL condition, const wchar_t* message)
{
    wprintf(message);
    if (condition)
    {
        wprintf(L"SUCCESS\n");
    }
    else
    {
        // TODO: Some cleanup
        wprintf(L"FAILURE (0x%x)\n", GetLastError());
        wprintf(L"\n<< Press any key to continue >>\n");
        _getch();
        exit(1);
    }
}
// End CheckError

// Main
void wmain(int argc, wchar_t* argv[])
{

    // Usage
    if (argc != 5) {
        ShowUsageAndExit();
    }

    if (!wcscmp(argv[1], L"s"))
    {
        // Sign
        Sign(argv[2], argv[3], argv[4]);
    }
    else if (!wcscmp(argv[1], L"v"))
    {
        // Verify
        Verify(argv[2], argv[3], argv[4]);
    }
    else
    {
        // Error
        ShowUsageAndExit();
    }

    // The end
    wprintf(L"\n<< Press any key to continue >>\n");
    _getch();

}
// End of main

// Sign
void Sign(wchar_t* SignerName, wchar_t* DataFileName, wchar_t* SignatureFileName)
{
    // Variables
    HCERTSTORE hStoreHandle = NULL;
    PCCERT_CONTEXT pSignerCert = NULL;
    HCRYPTPROV hCryptProv = NULL;
    DWORD dwKeySpec = 0;
    HCRYPTHASH hHash = NULL;
    HANDLE hDataFile = NULL;
    BOOL bResult = FALSE;
    BYTE rgbFile[BUFSIZE];
    DWORD cbRead = 0;
    DWORD dwSigLen = 0;
    BYTE* pbSignature = NULL;
    HANDLE hSignatureFile = NULL;
    DWORD lpNumberOfBytesWritten = 0;

    wprintf(L"SIGNING\n\n");

    // Open the certificate store.
    hStoreHandle = CertOpenStore(
        CERT_STORE_PROV_SYSTEM,
        0,
        NULL,
        CERT_SYSTEM_STORE_CURRENT_USER,
        CERT_PERSONAL_STORE_NAME
    );
    CheckError((BOOL)hStoreHandle, L"CertOpenStore....................... ");

    // Get signer's certificate with access to private key.
    do {
        // Get a certificate that matches the search criteria
        pSignerCert = CertFindCertificateInStore(
            hStoreHandle,
            MY_TYPE,
            0,
            CERT_FIND_SUBJECT_STR,
            SignerName,
            pSignerCert
        );
        CheckError((BOOL)pSignerCert, L"CertFindCertificateInStore.......... ");

        // Get the CSP, and check if we can sign with the private key           
        bResult = CryptAcquireCertificatePrivateKey(
            pSignerCert,
            0,
            NULL,
            &hCryptProv,
            &dwKeySpec,
            NULL
        );
        CheckError(bResult, L"CryptAcquireCertificatePrivateKey... ");

    } while ((dwKeySpec & AT_SIGNATURE) != AT_SIGNATURE);

    // Create the hash object.
    bResult = CryptCreateHash(
        hCryptProv,
        CALG_MD5,
        0,
        0,
        &hHash
    );
    CheckError(bResult, L"CryptCreateHash..................... ");

    // Open the file with the content to be signed 
    hDataFile = CreateFileW(DataFileName,
        GENERIC_READ,
        FILE_SHARE_READ,
        NULL,
        OPEN_EXISTING,
        FILE_FLAG_SEQUENTIAL_SCAN,
        NULL
    );
    CheckError((hDataFile != INVALID_HANDLE_VALUE), L"CreateFile.......................... ");

    // Compute the cryptographic hash of the data.
    while (bResult = ReadFile(hDataFile, rgbFile, BUFSIZE, &cbRead, NULL))
    {
        if (cbRead == 0)
        {
            break;
        }
        CheckError(bResult, L"ReadFile............................ ");

        bResult = CryptHashData(
            hHash,
            rgbFile,
            cbRead,
            0
        );
        CheckError(bResult, L"CryptHashData....................... ");

    }
    CheckError(bResult, L"ReadFile............................ ");

    // Sign the hash object
    dwSigLen = 0;
    bResult = CryptSignHash(
        hHash,
        AT_SIGNATURE,
        NULL,
        0,
        NULL,
        &dwSigLen
    );
    CheckError(bResult, L"CryptSignHash....................... ");

    pbSignature = (BYTE*)malloc(dwSigLen);
    CheckError((BOOL)pbSignature, L"malloc.............................. ");

    bResult = CryptSignHash(
        hHash,
        AT_SIGNATURE,
        NULL,
        0,
        pbSignature,
        &dwSigLen
    );
    CheckError(bResult, L"CryptSignHash....................... ");

    // Create a file to save the signature
    hSignatureFile = CreateFileW(
        SignatureFileName,
        GENERIC_WRITE,
        0,
        NULL,
        CREATE_ALWAYS,
        FILE_ATTRIBUTE_NORMAL,
        NULL
    );
    CheckError((hSignatureFile != INVALID_HANDLE_VALUE), L"CreateFile.......................... ");

    // Write the signature to the file
    bResult = WriteFile(
        hSignatureFile,
        (LPCVOID)pbSignature,
        dwSigLen,
        &lpNumberOfBytesWritten,
        NULL
    );
    CheckError(bResult, L"WriteFile........................... ");

    // Clean up and free memory.
    free(pbSignature);

    CloseHandle(hDataFile);
    CloseHandle(hSignatureFile);

    bResult = CryptDestroyHash(hHash);
    CheckError(bResult, L"CryptDestroyHash.................... ");

    bResult = CertFreeCertificateContext(pSignerCert);
    CheckError(bResult, L"CertFreeCertificateContext.......... ");

    bResult = CertCloseStore(
        hStoreHandle,
        CERT_CLOSE_STORE_CHECK_FLAG
    );
    CheckError(bResult, L"CertCloseStore...................... ");

}
// End of Sign

// Verify
void Verify(wchar_t* SignerName, wchar_t* DataFileName, wchar_t* SignatureFileName)
{
    // Variables
    HCERTSTORE hStoreHandle = NULL;
    PCCERT_CONTEXT pSignerCert = NULL;
    DWORD dwKeySpec = 0;
    HCRYPTPROV hCryptProv = NULL;
    HCRYPTHASH hHash = NULL;
    HANDLE hDataFile = NULL;
    BOOL bResult = FALSE;
    BYTE rgbFile[BUFSIZE];
    DWORD cbRead = 0;
    HANDLE hSignatureFile = NULL;
    BYTE* pbBinary = NULL;
    DWORD cbBinary = 0;
    HCRYPTKEY hPubKey = NULL;

    wprintf(L"VERIFYING\n\n");

    // Open the certificate store.
    hStoreHandle = CertOpenStore(
        CERT_STORE_PROV_SYSTEM,
        0,
        NULL,
        CERT_SYSTEM_STORE_CURRENT_USER,
        CERT_PERSONAL_STORE_NAME
    );
    CheckError((BOOL)hStoreHandle, L"CertOpenStore....................... ");

    // Get a certificate that matches the search criteria
    pSignerCert = CertFindCertificateInStore(
        hStoreHandle,
        MY_TYPE,
        0,
        CERT_FIND_SUBJECT_STR,
        SignerName,
        pSignerCert
    );
    CheckError((BOOL)pSignerCert, L"CertFindCertificateInStore.......... ");

    // Get the CSP
    bResult = CryptAcquireContext(
        &hCryptProv,
        NULL,
        NULL,
        PROV_RSA_FULL,
        CRYPT_VERIFYCONTEXT
    );
    CheckError(bResult, L"CryptAcquireContext................. ");

    // Create the hash object.
    bResult = CryptCreateHash(
        hCryptProv,
        CALG_MD5,
        0,
        0,
        &hHash
    );
    CheckError(bResult, L"CryptCreateHash..................... ");

    // Open the file with the content that was signed.
    hDataFile = CreateFileW(
        DataFileName,
        GENERIC_READ,
        FILE_SHARE_READ,
        NULL,
        OPEN_EXISTING,
        FILE_FLAG_SEQUENTIAL_SCAN,
        NULL
    );
    CheckError((hDataFile != INVALID_HANDLE_VALUE), L"CreateFile.......................... ");

    // Compute the cryptographic hash of the data.
    while (bResult = ReadFile(hDataFile, rgbFile, BUFSIZE, &cbRead, NULL))
    {
        if (cbRead == 0)
        {
            break;
        }
        CheckError(bResult, L"ReadFile............................ ");

        bResult = CryptHashData(
            hHash,
            rgbFile,
            cbRead,
            0
        );
        CheckError(bResult, L"CryptHashData....................... ");
    }
    CheckError(bResult, L"ReadFile............................ ");

    // Open the file with the signature
    hSignatureFile = CreateFileW(
        SignatureFileName,
        GENERIC_READ,
        FILE_SHARE_READ,
        NULL,
        OPEN_EXISTING,
        FILE_FLAG_SEQUENTIAL_SCAN,
        NULL
    );
    CheckError((hSignatureFile != INVALID_HANDLE_VALUE), L"CreateFile.......................... ");

    // Read the signature from the file
    pbBinary = (BYTE*)malloc(BUFSIZE);
    CheckError((BOOL)pbBinary, L"malloc.............................. ");

    bResult = ReadFile(hSignatureFile, pbBinary, BUFSIZE, &cbBinary, NULL);
    CheckError(bResult, L"ReadFile............................ ");

    // Get the public key from the certificate
    CryptImportPublicKeyInfo(
        hCryptProv,
        MY_TYPE,
        &pSignerCert->pCertInfo->SubjectPublicKeyInfo,
        &hPubKey
    );
    CheckError(bResult, L"CryptImportPublicKeyInfo............ ");

    // Verify the signature
    bResult = CryptVerifySignature(
        hHash,
        pbBinary,
        cbBinary,
        hPubKey,
        NULL,
        0
    );
    CheckError(bResult, L"CryptVerifySignature................ ");

    // Clean up and free memory.
    free(pbBinary);

    CloseHandle(hDataFile);
    CloseHandle(hSignatureFile);

    bResult = CryptDestroyHash(hHash);
    CheckError(bResult, L"CryptDestroyHash.................... ");

    bResult = CertFreeCertificateContext(pSignerCert);
    CheckError(bResult, L"CertFreeCertificateContext.......... ");

    bResult = CertCloseStore(
        hStoreHandle,
        CERT_CLOSE_STORE_CHECK_FLAG
    );
    CheckError(bResult, L"CertCloseStore...................... ");

    bResult = CryptReleaseContext(
        hCryptProv,
        0
    );
    CheckError(bResult, L"CryptReleaseContext................. ");
}
// End of Verify
```





代码3

```

#include <stdio.h>
#include <direct.h>
#include <string>
#include <vector>
#include <Windows.h>
#include <Wincrypt.h>
using namespace std;
#pragma warning(disable:4996)
#pragma comment(lib, "Crypt32")
#define PROV_LENGTH 255
#define MY_ENCODING_TYPE  (PKCS_7_ASN_ENCODING | X509_ASN_ENCODING)

struct keyInfo
{
    string certUserName;
    string providerName;
    string containerName;
};

void GetListProvider(vector<string>& vProList)
{
    DWORD dwIndex = 0;
    DWORD dwType = 0;
    char szKeyName[PROV_LENGTH] = { 0 };
    DWORD dwMaxSubKey = PROV_LENGTH;
    while (CryptEnumProviders(dwIndex++, NULL, 0, &dwType, (LPWSTR)szKeyName, &dwMaxSubKey))
    {
        int num = WideCharToMultiByte(CP_OEMCP, NULL, (LPWSTR)szKeyName, -1, NULL, 0, NULL, FALSE);
        char* pchar = new char[num];
        WideCharToMultiByte(CP_OEMCP, NULL, (LPWSTR)szKeyName, -1, pchar, num, NULL, FALSE);
        if (dwType == PROV_RSA_FULL && strnicmp("Microsoft", pchar, 9)) //csp类型 + 不区分大小写比较前9个字符
        {
            vProList.push_back(pchar);
        }
        memset(szKeyName, 0, PROV_LENGTH);
        dwMaxSubKey = PROV_LENGTH;
    }
}

DWORD GetCertificate(HCRYPTPROV hProv, BYTE* pCertificate, DWORD* pCertificateLen)
{
    if (hProv == NULL) return -1;

    //获取所获取密钥类型的句柄
    //获取公私钥对和交换密钥,公私钥用来签名,而交换密钥用来导出会话密钥
    HCRYPTKEY hSignKey;
    if (CryptGetUserKey(hProv, AT_SIGNATURE, &hSignKey) == FALSE)
    {
        printf("CSP获取密钥句柄失败\n");
        return -1;
    }
    if (CryptGetKeyParam(hSignKey, KP_CERTIFICATE, pCertificate, pCertificateLen, 0) == FALSE)
    {
        printf("CSP得到密钥参数失败\n");
        return -1;
    }
    if (CryptDestroyKey(hSignKey) == FALSE)
    {
        printf("CSP销毁密钥失败\n");
        return -1;
    }
    return 0;
}

DWORD DecodeX509Cert(BYTE* pCertificate, DWORD certificateLen, char keyID[255], char userName[255])
{
    PCCERT_CONTEXT pctx = CertCreateCertificateContext(MY_ENCODING_TYPE, pCertificate, certificateLen); //从编码证书中创建一个证书上下文。但这个上下文并不放到证书库里
    if (pctx == NULL)
    {
        printf("解析证书失败\n");
        return -1;
    }

    //找到扩展对象
    PCERT_EXTENSION pCertExt = CertFindExtension(szOID_AUTHORITY_KEY_IDENTIFIER2, pctx->pCertInfo->cExtension, pctx->pCertInfo->rgExtension); //通过OID来查找扩展
    if (!pCertExt)
    {
        printf("证书属性不存在\n");
        return -1;
    }

    //解码对象,得到属性结构体
    DWORD ulDataLen = 512;
    BYTE btData[512] = { 0 };
    CHAR csProperty[512] = { 0 };
    PCERT_AUTHORITY_KEY_ID2_INFO pAuthorityKeyID2 = (PCERT_AUTHORITY_KEY_ID2_INFO)btData;
    if (CryptDecodeObject(MY_ENCODING_TYPE, szOID_AUTHORITY_KEY_IDENTIFIER2,
        pCertExt->Value.pbData, pCertExt->Value.cbData, CRYPT_DECODE_NOCOPY_FLAG, pAuthorityKeyID2, &ulDataLen)) //对属性结构体进行解码
    {
        //获取颁发机构标识符
        for (ULONG ulIndex = 0; ulIndex < pAuthorityKeyID2->KeyId.cbData; ulIndex++)
        {
            CHAR csKeyID[8] = { 0 };
            sprintf_s(csKeyID, 8, "%02x ", pAuthorityKeyID2->KeyId.pbData[ulIndex]);
            strcat_s(csProperty, 512, csKeyID);
        }
        csProperty[strlen(csProperty) - 1] = 0;
        strcpy(keyID, csProperty);

        //获取userName
        TCHAR sName[255];
        DWORD nNameSize = 255;
        DWORD nNameType = 0;
        CertGetNameString(pctx, CERT_NAME_SIMPLE_DISPLAY_TYPE, 0, &nNameType, sName, nNameSize); //得到证书的主题或颁发者名称并且把它转换成字符串
        strcpy(userName, (char*)sName);
        CertFreeCertificateContext(pctx);
        return 0;
    }
    else
    {
        CertFreeCertificateContext(pctx);
        return -1;
    }
}


int getKeyList()
{
    //1.获取注册表中的Provider
    printf("\n**1**---------------------------------获取Provider-------------------------------\n");
    vector<string> vProvList;
    GetListProvider(vProvList);
    int numProv = vProvList.size();
    if (numProv == 0)
    {
        printf("获取注册表中的Provider失败\n");
        return -1;
    }
    for (int i = 0; i < numProv; i++)
    {
        printf("%s\n", vProvList[i].c_str());
    }
    printf("\n");

    vector<string> vContainer;

    printf("\n**2**---------------------------------取出Container-------------------------------\n");
    //2.获取Provider对应的句柄，枚举Container
    for (int i = 0; i < numProv; i++)
    {
        HCRYPTPROV hProv = NULL;
        size_t size = vProvList[i].length();
        wchar_t* buffer = new wchar_t[size + 1];
        MultiByteToWideChar(CP_ACP, 0, vProvList[i].c_str(), size, buffer, size * sizeof(wchar_t));
        buffer[size] = 0;  //确保以 '\0' 结尾 
        if (CryptAcquireContext(&hProv, NULL, buffer, PROV_RSA_FULL, 0)) //获取Provider对应的句柄
        {
            printf("%s---ok\n", vProvList[i].c_str());
            BYTE pbData[512] = { 0 };
            DWORD cbData = 512;
            if (CryptGetProvParam(hProv, PP_ENUMCONTAINERS, pbData, &cbData, CRYPT_FIRST)) //枚举container
            {
                printf("[%s]\n", pbData);
                vContainer.push_back((char*)pbData);
                memset(pbData, 0, sizeof(pbData));
                cbData = 512;
                while (CryptGetProvParam(hProv, PP_ENUMCONTAINERS, pbData, &cbData, CRYPT_NEXT))
                {
                    printf("[%s]\n", pbData);
                    vContainer.push_back((char*)pbData);

                    memset(pbData, 0, sizeof(pbData));
                    cbData = 512;
                }
            }
            else
            {
                DWORD errcode = GetLastError();
                printf("[%s]CryptGetProvParam失败！--%d\n", vProvList[i].c_str(), errcode);
            }

            CryptReleaseContext(hProv, 0);
            printf("\n");
        }
        else
        {
            DWORD errcode = GetLastError();
            printf("[%s]------CryptAcquireContext失败！---%d\n", vProvList[i].c_str(), errcode);
        }
    }

    printf("\n**3**---------------------------------取出Container对应证书-------------------------------\n");
    //3.打开Container、Provider对应的加密设备，取出签名证书，取出证书信息（颁发机构标识符等）
    for (size_t i = 0; i < vContainer.size(); i++)
    {
        for (int j = 0; j < numProv; j++)
        {
            HCRYPTPROV hProv = NULL;
            if (CryptAcquireContext(&hProv, (LPCWSTR)vContainer[i].c_str(), (LPCWSTR)vProvList[j].c_str(), PROV_RSA_FULL, 0))
            {
                printf("[%s]------open container ok\n", vContainer[i].c_str());
                BYTE pbData[512] = { 0 };
                DWORD cbData = 512;

                BYTE pCertificate[4096] = { 0 };
                DWORD certificateLen = 4096;
                if (!GetCertificate(hProv, pCertificate, &certificateLen)) //获取签名证书
                {
                    char userName[255] = { 0 };
                    char keyID[255] = { 0 };
                    if (!DecodeX509Cert(pCertificate, certificateLen, keyID, userName)) //获取证书【颁发机构标识符】-【用户名】
                    {
                        printf(">>>>>>>>>>>>>证书信息\n");
                        printf("keyID=%s\n", keyID);
                        printf("userName=%s\n", userName);
                    }
                }
                CryptReleaseContext(hProv, 0);
                printf("\n");
            }
            else
            {
                printf("[%s]------CryptAcquireContext失败！\n", vProvList[j].c_str());
            }
        }
    }
    return 0;
}

int main()
{
    char buffer[100] = { 0 };
    getcwd(buffer, 100);
    printf("%s\n", buffer);

    getKeyList();
    printf("\n\n########################################################################\n\n");
    Sleep(1000);

    getKeyList();
    getchar();
    return 0;
}

```



代码2

```
#include <stdio.h>
#include <stdlib.h>
#include <windows.h>
#include <wincrypt.h>


void main() {
	HCRYPTPROV hProv;
	BYTE* puBuffer = (BYTE*)"data hash and sign.one plus.";
	DWORD dwBufferLen = strlen((char*)puBuffer) + 1;
	HCRYPTHASH hHash;
	HCRYPTKEY hKey;    //签名密钥句柄
	HCRYPTKEY hPubKey;
	BYTE* pbKeyBlob;  //保存密钥blob缓冲区指针
	BYTE* pbSignature;
	DWORD dwSigLen;
	DWORD dwBlobLen;
	DWORD i;
	if (CryptAcquireContext(&hProv, (LPCWSTR)"test", NULL, PROV_RSA_FULL, 0))
		printf("打开句柄成功\n");
	else {
		if (!CryptAcquireContext(&hProv, (LPCWSTR)"test", NULL, PROV_RSA_FULL, CRYPT_NEWKEYSET))
			printf("创建失败。\n");
	}
	if (CryptGetUserKey(hProv, AT_SIGNATURE, &hKey))
		printf("获得签名密钥成功。\n");
	else {
		printf("获取失败，现在创建新的RSA密钥对。\n");
		if (!CryptAcquireContext(&hProv, (LPCWSTR)"test", NULL, PROV_RSA_FULL, 0))
			printf("获取CSP句柄失败\n");
		if (!CryptGenKey(hProv, 2, CRYPT_EXPORTABLE | 0X04000000, &hKey))
			printf("CryptGenKey error.\n");
	}
	if (CryptExportKey(hKey, NULL, PUBLICKEYBLOB, 0, NULL, &dwBlobLen))
		printf("we get the length of the public key.\n");
	else
		printf("CryptExportKey erro.\n");
	if (pbKeyBlob = (BYTE*)malloc(dwBlobLen))
		printf("we get the memory.\n");
	else
		printf("malloc erro.\n");
	if (CryptExportKey(hKey, NULL, PUBLICKEYBLOB, 0, pbKeyBlob, &dwBlobLen))
		printf("export the public key.\n");
	else
		printf("CryptExportKeya error.\n");
	if (CryptCreateHash(hProv, CALG_SHA1, 0, 0, &hHash))
		printf("CreateHash succeed.\n");
	else
		printf("CreatHash error.\n");
	//获得摘要
	if (CryptHashData(hHash, puBuffer, dwBufferLen, 0))
		printf("HashData succeed.\n ");
	else
		printf("HashData error.\n");
	dwSigLen = 0;
	if (CryptSignHash(hHash, AT_SIGNATURE, NULL, 0, NULL, &dwSigLen))
		printf("Get the length of signature.\n");
	else
		printf("CryptSignHash error.\n");
	if (pbSignature = (BYTE*)malloc(dwSigLen))
		printf("get the memory.\n");
	else
		printf("memory error.\n");
	//获得签名
	if (CryptSignHash(hHash, AT_SIGNATURE, NULL, 0, pbSignature, &dwSigLen))
		printf("signature succeed.\n");
	else
		printf("Signature error.\n");
	printf("Signature: \n");
	for (i = 0; i < dwSigLen; i++) {
		if ((i == 0) && (i != 0))
			printf("\n");
		//小数点后的数字代表最大宽度,小数点前的数字代表最小宽度，十六进制输出
		printf("%2.2x", pbSignature[i]);
	}
	printf("\n");
	printf("OK.\n");
	if (hHash)
		CryptDestroyHash(hHash);
	if (CryptImportKey(hProv, pbKeyBlob, dwBlobLen, 0, 0, &hPubKey))
		printf("Import the key.\n");
	else
		printf("erro");
	if (CryptCreateHash(hProv, CALG_SHA1, 0, 0, &hHash))
		printf("创建哈希对象成功 \n");
	else
		printf("调用CryptCreateHash失败");
	if (CryptHashData(hHash, puBuffer, dwBufferLen, 0))
		printf("数据哈希完成.\n");
	else
		printf("调用CryptHashData失败");
	if (CryptVerifySignature(hHash, pbSignature, dwSigLen, hPubKey, NULL, 0))
		printf("验证签名成功。\n");
	else
		printf("签名验证失败，签名无效");
	if (pbSignature)
		free(pbSignature);
	if (hHash)
		CryptDestroyHash(hHash);
	if (hProv)
		CryptReleaseContext(hProv, 0);
	system("pause");
}
```



代码1

```
#include <stdio.h>
#include <windows.h>
#include <wincrypt.h>
#define MY_ENCODING_TYPE  (PKCS_7_ASN_ENCODING | X509_ASN_ENCODING)
#define KEYLENGTH  0x00800000
void HandleError(const char* s);

//--------------------------------------------------------------------
//  These additional #define statements are required.
#define ENCRYPT_ALGORITHM CALG_RC4 
#define ENCRYPT_BLOCK_SIZE 8 

//   Declare the function EncryptFile. The function definition
//   follows main.

BOOL EncryptFile(
    PCHAR szSource,
    PCHAR szDestination,
    PCHAR szPassword);

//--------------------------------------------------------------------
//   Begin main.

void main(void)
{
    CHAR szSource[100];
    CHAR szDestination[100];
    CHAR szPassword[100];


    printf("Encrypt a file. \n\n");
    printf("Enter the name of the file to be encrypted: ");
    scanf("%s", szSource);
    printf("Enter the name of the output file: ");
    scanf("%s", szDestination);
    printf("Enter the password:");
    scanf("%s", szPassword);

    //--------------------------------------------------------------------
    // Call EncryptFile to do the actual encryption.

    if (EncryptFile(szSource, szDestination, szPassword))
    {
        printf("Encryption of the file %s was a success. \n", szSource);
        printf("The encrypted data is in file %s.\n", szDestination);
    }
    else
    {
        HandleError("Error encrypting file!");
    }
} // End of main

//--------------------------------------------------------------------
//   Code for the function EncryptFile called by main.

static BOOL EncryptFile(
    PCHAR szSource,
    PCHAR szDestination,
    PCHAR szPassword)
    //--------------------------------------------------------------------
    //   Parameters passed are:
    //     szSource, the name of the input, a plaintext file.
    //     szDestination, the name of the output, an encrypted file to be 
    //         created.
    //     szPassword, the password.
{
    //--------------------------------------------------------------------
    //   Declare and initialize local variables.

    FILE* hSource;
    FILE* hDestination;

    HCRYPTPROV hCryptProv;
    HCRYPTKEY hKey;
    HCRYPTHASH hHash;

    PBYTE pbBuffer;
    DWORD dwBlockLen;
    DWORD dwBufferLen;
    DWORD dwCount;

    //--------------------------------------------------------------------
    // Open source file. 
    if (hSource = fopen(szSource, "rb"))
    {
        printf("The source plaintext file, %s, is open. \n", szSource);
    }
    else
    {
        HandleError("Error opening source plaintext file!");
    }

    //--------------------------------------------------------------------
    // Open destination file. 
    if (hDestination = fopen(szDestination, "wb"))
    {
        printf("Destination file %s is open. \n", szDestination);
    }
    else
    {
        HandleError("Error opening destination ciphertext file!");
    }

    //以下获得一个CSP句柄
    if (CryptAcquireContext(
        &hCryptProv,
        NULL,    //NULL表示使用默认密钥容器，默认密钥容器名
        //为用户登陆名
        NULL,
        PROV_RSA_FULL,
        0))
    {
        printf("A cryptographic provider has been acquired. \n");
    }
    else
    {
        if (CryptAcquireContext(
            &hCryptProv,
            NULL,
            NULL,
            PROV_RSA_FULL,
            CRYPT_NEWKEYSET))//创建密钥容器
        {
            //创建密钥容器成功，并得到CSP句柄
            printf("A new key container has been created.\n");
        }
        else
        {
            HandleError("Could not create a new key container.\n");
        }

    }

    //--------------------------------------------------------------------
    // 创建一个会话密钥（session key）
    // 会话密钥也叫对称密钥，用于对称加密算法。
    // （注: 一个Session是指从调用函数CryptAcquireContext到调用函数
    //   CryptReleaseContext 期间的阶段。会话密钥只能存在于一个会话过程）

    //--------------------------------------------------------------------
    // Create a hash object. 
    if (CryptCreateHash(
        hCryptProv,
        CALG_MD5,
        0,
        0,
        &hHash))
    {
        printf("A hash object has been created. \n");
    }
    else
    {
        HandleError("Error during CryptCreateHash!\n");
    }

    //--------------------------------------------------------------------
    // 用输入的密码产生一个散列
    if (CryptHashData(
        hHash,
        (BYTE*)szPassword,
        strlen(szPassword),
        0))
    {
        printf("The password has been added to the hash. \n");
    }
    else
    {
        HandleError("Error during CryptHashData. \n");
    }

    //--------------------------------------------------------------------
    // 通过散列生成会话密钥
    if (CryptDeriveKey(
        hCryptProv,
        ENCRYPT_ALGORITHM,
        hHash,
        KEYLENGTH,
        &hKey))
    {
        printf("An encryption key is derived from the password hash. \n");
    }
    else
    {
        HandleError("Error during CryptDeriveKey!\n");
    }
    //--------------------------------------------------------------------
    // Destroy the hash object. 

    CryptDestroyHash(hHash);
    hHash = NULL;

    //--------------------------------------------------------------------
    //  The session key is now ready. 

    //--------------------------------------------------------------------
    // 因为加密算法是按ENCRYPT_BLOCK_SIZE 大小的块加密的，所以被加密的
   // 数据长度必须是ENCRYPT_BLOCK_SIZE 的整数倍。下面计算一次加密的
   // 数据长度。

    dwBlockLen = 1000 - 1000 % ENCRYPT_BLOCK_SIZE;

    //--------------------------------------------------------------------
    // Determine the block size. If a block cipher is used, 
    // it must have room for an extra block. 

    if (ENCRYPT_BLOCK_SIZE > 1)
        dwBufferLen = dwBlockLen + ENCRYPT_BLOCK_SIZE;
    else
        dwBufferLen = dwBlockLen;

    //--------------------------------------------------------------------
    // Allocate memory. 
    if (pbBuffer = (BYTE*)malloc(dwBufferLen))
    {
        printf("Memory has been allocated for the buffer. \n");
    }
    else
    {
        HandleError("Out of memory. \n");
    }
    //--------------------------------------------------------------------
    // In a do loop, encrypt the source file and write to the source file. 

    do
    {

        //--------------------------------------------------------------------
        // Read up to dwBlockLen bytes from the source file. 
        dwCount = fread(pbBuffer, 1, dwBlockLen, hSource);
        if (ferror(hSource))
        {
            HandleError("Error reading plaintext!\n");
        }

        //--------------------------------------------------------------------
        // 加密数据
        if (!CryptEncrypt(
            hKey,   //密钥
            0,    //如果数据同时进行散列和加密，这里传入一个
         //散列对象
            feof(hSource), //如果是最后一个被加密的块，输入TRUE.如果不是输
                //入FALSE这里通过判断是否到文件尾来决定是否为
         //最后一块。
            0,    //保留
            pbBuffer,  //输入被加密数据，输出加密后的数据
            &dwCount,  //输入被加密数据实际长度，输出加密后数据长度
            dwBufferLen)) //pbBuffer的大小。
        {
            HandleError("Error during CryptEncrypt. \n");
        }

        //--------------------------------------------------------------------
        // Write data to the destination file. 

        fwrite(pbBuffer, 1, dwCount, hDestination);
        if (ferror(hDestination))
        {
            HandleError("Error writing ciphertext.");
        }

    } while (!feof(hSource));
    //--------------------------------------------------------------------
    //  End the do loop when the last block of the source file has been
    //  read, encrypted, and written to the destination file.

    //--------------------------------------------------------------------
    // Close files.

    if (hSource)
        fclose(hSource);
    if (hDestination)
        fclose(hDestination);

    //--------------------------------------------------------------------
    // Free memory. 

    if (pbBuffer)
        free(pbBuffer);

    //--------------------------------------------------------------------
    // Destroy session key. 

    if (hKey)
        CryptDestroyKey(hKey);

    //--------------------------------------------------------------------
    // Destroy hash object. 

    if (hHash)
        CryptDestroyHash(hHash);

    //--------------------------------------------------------------------
    // Release provider handle. 

    if (hCryptProv)
        CryptReleaseContext(hCryptProv, 0);
    return(TRUE);
} // End of Encryptfile

//--------------------------------------------------------------------
//  This example uses the function HandleError, a simple error
//  handling function, to print an error message to the standard error 
//  (stderr) file and exit the program. 
//  For most applications, replace this function with one 
//  that does more extensive error reporting.

void HandleError(const char* s)
{
    fprintf(stderr, "An error occurred in running the program. \n");
    fprintf(stderr, "%s\n", s);
    fprintf(stderr, "Error number %x.\n", GetLastError());
    fprintf(stderr, "Program terminating. \n");
    exit(1);
} // End of HandleError
```

