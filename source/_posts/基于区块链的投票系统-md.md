---
title: 基于区块链的投票系统
toc: true
date: 2021-01-20 12:54:24
tags:
categories: 其他
---

# 知识背景

truffle快速开始：

https://learnblockchain.cn/docs/truffle/quickstart.html

webpack

https://webpack.js.org/concepts/configuration/

https://github.com/truffle-box/webpack-box

web3js

https://learnblockchain.cn/docs/web3.js/web3-eth-contract.html#methods-mymethod-send

solidity

https://learnblockchain.cn/docs/solidity/

remix

https://remix.ethereum.org/

# 基于truffle框架的投票DAPP



## 安装truffle

sudo npm install -g truffle

**安装过程中可能会报错，如果报错则执行如下命令升级nodejs**:

```
sudo npm install -g n
sudo n stable
```

* truffle安装完成后用如下命令检查：

```
truffle -v
```

## 创建项目工程

```
truffle unbox webpack 
```

* 配置truffle_config.js文件

```
networks下：
    development: {
     host: "127.0.0.1",     // Localhost (default: none)
     port: 8545,            // Standard Ethereum port (default: none)
     network_id: "*",       // Any network (default: none)
     gas: 470000
    },
```
* 将 2_deploy_contracts.js 的内容更新为以下信息
```
const Voting = artifacts.require("./Voting.sol");

module.exports = function(deployer) {
  deployer.deploy(Voting, [web3.utils.toHex('Alice'),web3.utils.toHex('Trump')]); //转十六进制
};

```



## 启动测试链ganache

https://www.trufflesuite.com/docs/ganache/workspaces/ethereum-workspace-overview

https://blog.csdn.net/qq_20513027/article/details/85041353
1）输入

sudo npm install -g ganache-cli
2）启动

ganache-cli

## 编译部署

```
truffle compile
truffle migrate

```



## 控制台交互

Voting.deployed().then(function(contractInstance){contractInstance.voteForCandidate(web3.utils.toHex('Bob')).then(function(v) {console.log(v)})}) 



Voting.deployed().then(function(contractInstance) {contractInstance.totalVotesFor.call(web3.utils.toHex('Alice')).then(function(v) {console.log(v.toString())})}) 



## 网页交互

https://www.jianshu.com/p/f8d41a5d909d

https://myblog.gumptlu.work/2020/08/23/%E6%8A%80%E6%9C%AF%E8%B4%B4/%E5%8C%BA%E5%9D%97%E9%93%BE/truffle%E6%A1%86%E6%9E%B6%E7%9A%84%E5%AD%A6%E4%B9%A0/

https://github.com/truffle-box/webpack-box

npm run dev报错很有可能启动需要的依赖包没有安装，安装的方式是在项目文件夹下：

npm install



# geth命令

eth.getBalance(eth.accounts[1])

* 导入账户

personal.importRawKey("3b2e3e081c5450a25e4a859b5a5db70f6e0291cb87b2596b4739485132825883","jkslks5965")

* 解锁：* 

personal.unlockAccount(eth.accounts[0],"123456", 600000)

eth.sendTransaction({from: eth.accounts[1],gasPrice: "20000000000",gas: "21000",to: eth.accounts[0],value: "1000000000000000000",data: ""}, 'jkslks5965')

eth.sendTransaction({from: "0x1544b84b06ace59dcac798f2eca6b9577bafd612",to: "0x52e04f1b40891060beb72ea09d2b873bdb7ea7cb",value: '1000000000000000000'})

eth.sendTransaction({from: eth.accounts[0],to: eth.accounts[2],amount})

* 设置以太

amount = web3.toWei(2,'ether')

eth.sendTransaction({from: eth.accounts[0], to: eth.accounts[1], value: amount})

eth.sendTransaction({from: eth.accounts[0], to: eth.accounts[1], value: web3.toWei(10,'ether')})

* 交易完成后，要挖矿才可以交易成功



* 解锁账户

web3.eth.personal.unlockAccount('0x1544b84B06ace59DcAc798f2Eca6B9577BAfD612','123456',300000)



# 使用fabric-samples测试网络环境配置

<!--more-->

由于网络问题，自行下载三个包

https://pan.baidu.com/s/1-zq-MJlIb0Etn_8O0nT10w#list/path=%2F

提取码：zn0v

https://zhuanlan.zhihu.com/p/158381955

遇到错误

ERROR! Peer binary and configuration files not found..

https://www.codenong.com/cs106355725/

官方地址

https://hyperledger-fabric.readthedocs.io/zh_CN/release-2.2/install.html

# 使用Fabric的测试网络

官方地址：

https://hyperledger-fabric.readthedocs.io/zh_CN/release-2.2/test_network.html#

相关博客：

https://my.oschina.net/zfjian/blog/4943514

https://blog.csdn.net/ngums/article/details/109170721

# 将智能合约部署到渠道

https://hyperledger-fabric.readthedocs.io/zh_CN/release-2.2/deploy_chaincode.html#install-the-chaincode-package

go mod有错误，改成go mod download，再go mod vendor

org1和org2的环境变量需分开配置









# 安装node

https://cloud.tencent.com/document/product/213/38237

**方法二：使用代理registry**

在网上查阅了一些资料后，决定使用代理的方式，方法也很简单，就是

```
npm config set registry https://registry.npm.taobao.org
```



# 编译

在项目目录中创建如下目录

mkdir contracts 
mkdir scripts 
mkdir compiled 
mkdir tests 

安装依赖

npm install solc @0.4.25

npm list  fs-extra //查看版本信息

合约

```
pragma solidity ^0.4.22; 
 
contract Voting { 
 mapping (bytes32 => uint8) public votesReceived;   
 bytes32[] public candidateList;    constructor(bytes32[] candidateNames) public { 
  candidateList = candidateNames;   
 }    function totalVotesFor(bytes32 candidate) view public returns (uint8) { 
   require(validCandidate(candidate));     
  return votesReceived[candidate];   
 } 
 function voteForCandidate(bytes32 candidate) public { 
      require(validCandidate(candidate));     
  votesReceived[candidate]  += 1; 
 }    function validCandidate(bytes32 candidate) view public returns (bool) { 
  for(uint i = 0; i < candidateList.length; i++) { 
   if (candidateList[i] == candidate) { 
           return true;  
           }     
  }     
  return false;    
 } 
} 
```



编译脚本

```
const fs = require('fs-extra');  
const path = require('path');  
const solc = require('solc');  
 
// cleanup  
const compiledDir = path.resolve(__dirname, '../compiled'); 
fs.removeSync(compiledDir);  
fs.ensureDirSync(compiledDir); 
// compile 
const contractPath = path.resolve(__dirname,  
    '../contracts', 'Voting.sol');  
const contractSource = fs.readFileSync(contractPath, 'utf8');  
const result = solc.compile(contractSource, 1);  
 
// check errors  
if (Array.isArray(result.errors) && result.errors.length) { 
 throw new Error(result.errors[0]);  
} 
 
// save to disk  
Object.keys(result.contracts).forEach(name => {  
const contractName = name.replace(/^:/, '');  
const filePath = path.resolve(compiledDir,  
    `${contractName}.json`);  
fs.outputJsonSync(filePath, result.contracts[name]);  
console.log(`save compiled contract ${contractName} to  
  ${filePath}`); });  
 
```

编译命令

node ./scripts/compile.js



# 用truffle构建简单dapp

## 连接到私链的控制台

Geth
Version: 1.8.0-stable



* 搭建私链文件genesis.json 
  {
  	"config": {
           	"chainId": 15     
  	},     
  	"difficulty": "1800",     
  	"gasLimit": "2100000",     
  	"alloc": {
  		"0x52e04F1b40891060bEB72ea09d2B873bdb7ea7CB": { "balance": "30000000000000000000000" }
  	}
  }

geth attach http://127.0.0.1:8545

* 查看日志

tail -f output.log

* 查看geth

ps -ef | grep geth

* 初始化私链

geth --datadir . init genesis.json 

* 启动私链

nohup geth --datadir . --networkid 15 --rpc --rpcapi db,eth,net,web3,personal,miner  --rpcport 8545 --rpcaddr 127.0.0.1 --rpccorsdomain "*" 2>output.log & 



nohup geth --datadir . --networkid 16 --rpc --rpcapi db,eth,net,web3,personal,miner  --rpcport 8545 --rpcaddr 127.0.0.1 --rpccorsdomain "*" 2>output.log & 

nohup geth --datadir . --dev.period=1 --networkid 15 --rpc --rpcapi db,eth,net,web3,personal,miner  --rpcport 8545 --rpcaddr 127.0.0.1 --allow-insecure-unlock --rpccorsdomain "*" 2>output.log & 



nohup geth --datadir . --dev.period=1 --networkid 16 --rpc --rpcapi db,eth,net,web3,personal,miner  --rpcport 8545 --rpcaddr 127.0.0.1 --allow-insecure-unlock --rpccorsdomain "*" 2>output.log & 



// --dev.period 1允许挖矿

https://www.cnblogs.com/lvdongjie/p/11205885.html

//--allow-insecure-unlock允许解锁



```
<!DOCTYPE html>
<html>
    <head>
        <title>Voting DApp</title>
        <!--
        <link href='https://fonts.googleapis.com/css?family=Open Sans:400,700' rel='stylesheet' type='text/css'>
        -->
        <link href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css' rel='stylesheet' type='text/css'>
    </head>
    <body class="container">
        <h1>A Simple Voting Application</h1>
        <div id="address"></div> 
        <div class="table-responsive">
            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th>Candidate</th>
                        <th>Votes</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>Alice</td>
                        <td id="candidate-1"></td>
                    </tr>
                    <tr>
                        <td>Bob</td>
                        <td id="candidate-2"></td>
                    </tr>
                    <tr>
                        <td>Cary</td>
                        <td id="candidate-3"></td>
                    </tr>
                </tbody>
            </table>
            <div id="msg"></div> 
        </div>
        <input type="text" id="candidate" />
        <a href="#" onclick="App.voteForCandidate()" class="btn btn-primary">Vote</a>
    </body>
    <!--
    <script src="https://cdn.rawgit.com/ethereum/web3.js/develop/dist/web3.js"></script>
    -->
    <script src="https://cdn.jsdelivr.net/gh/ethereum/web3.js/dist/web3.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.1.1.slim.min.js"></script>
    <script src="./index.js"></script>
</html>
```

```
import Web3 from "web3";
import Voting from "../../build/contracts/Voting.json";
let candidates = {"Alice": "candidate-1", "Bob": "candidate-2", "Cary": "candidate-3"}
const App = {
  web3: null,
  account: null,
  vote: null,

  start: async function() {
    const { web3 } = this;

    try {
      // get contract instance
      const networkId = await web3.eth.net.getId();
      const deployedNetwork = Voting.networks[networkId];
      //console.log(Voting.networks[networkId]);
      this.vote = new web3.eth.Contract(
        Voting.abi,
        deployedNetwork.address,
      );
      // console.log(Voting.abi);
      // console.log(deployedNetwork.address);
      // console.log(this.vote);

      this.refreshVotes();
      // get accounts
      const accounts = await web3.eth.getAccounts();
      this.account = accounts[0];

      //this.refreshBalance();
    } catch (error) {
      console.error("Could not connect to contract or chain.");
    }
  },

  refreshVotes: async function() {
    const { web3 } = this;
    try{
      let candidateNames = Object.keys(candidates);  
      for (var i = 0; i < candidateNames.length; i++) {   
        let name = candidateNames[i];          
        const { totalVotesFor } = this.vote.methods;
        //console.log(name);
        const value = await totalVotesFor(web3.utils.toHex(name)).call();
        //console.log(value);
        $("#" + candidates[name]).html(value.toString()); 
      } 
    }catch(e){
      console.error(e);
    }
  },

  voteForCandidate: async function() {
    const { web3 } = this;
    try{
      $("#msg").val("vote submited");
      let canName = $("#candidate").val();
      $("#candidate").val('');
      console.log(canName);
      const { voteForCandidate } = this.vote.methods;
      console.log(this.vote.methods);
      console.log(voteForCandidate);
      await voteForCandidate(web3.utils.toHex(canName)).send({ from: this.account });
      this.refreshVotes();
    }catch(err){
      console.error(err);
    }
  },

};

window.App = App;

window.addEventListener("load", function() {
  if (null) {
    $("#msg").html('helo');
    // use MetaMask's provider
    App.web3 = new Web3(window.ethereum);
    window.ethereum.enable(); // get permission to access accounts
  } else {
    // fallback - use your fallback strategy (local node / hosted node + in-dapp id mgmt / fail)
    App.web3 = new Web3(
      new Web3.providers.HttpProvider("http://127.0.0.1:8545"),
    );

  }

  App.start();
});

```

```
    development: {
     host: "127.0.0.1",     // Localhost (default: none)
     port: 8545,            // Standard Ethereum port (default: none)
     network_id: "*",       // Any network (default: none)
    },
    
```



```
const Voting = artifacts.require("./Voting.sol");

module.exports = function(deployer) {
  deployer.deploy(Voting, [web3.utils.toHex('Alice'),web3.utils.toHex('Bob'),web3.utils.toHex('Cary')]); 
};
```

(0) 0x1084DF50306Ce706e10eb950C571F833Bc8480b6 (100 ETH)
(1) 0xc43eF32Db84cF8EcE3c3ec59610C8c5767219d87 (100 ETH)
(2) 0x7fcC051B830F1C781986E8B8225240dF86C9d4AA (100 ETH)
(3) 0x892E3b2fc8e0Afa618f9931142f88fA5fff5b4b9 (100 ETH)
(4) 0xd8b9375a2640fea9a3262AA21e095dAb6a1ad1eb (100 ETH)
(5) 0xd4eAc56BAd7C1f5a01026B98a5fB2393211D2Fe0 (100 ETH)
(6) 0x4C3e9e2b9Bca092d7569C005Bd542dB8e281bD44 (100 ETH)
(7) 0x94A4dC26Ed361D9b65d81A782b378770Ae825737 (100 ETH)
(8) 0xB742F4391435E5B97075cBe073B1B8D51C641298 (100 ETH)
(9) 0xA5D19e3eab28a5bD402fc8DBFC6F813afebbab0D (100 ETH)

# 遇到的错误



## 'webpack-dev-server' 不是内部或外部命令，也不是可运行的程序

命令：***npm install webpack-dev-server --save***

https://blog.csdn.net/hzxOnlineOk/article/details/78284101

## solc的版本问题

Error: Truffle is currently using solc 0.5.16, but one or more of your contracts specify "pragma solidity ^0.4.18".
Please update your truffle config or pragma statement(s).
(See https://trufflesuite.com/docs/truffle/reference/configuration#compiler-configuration for information on
configuring Truffle to use a specific solc compiler version.)



把truffle-config

改成

 compilers: {

  solc: {

   version: "0.4.22",  // Fetch exact version from solc-bin (default: truffle's version)

   // docker: true,    // Use "0.5.1" you've installed locally with docker (default: false)

   // settings: {     // See the solidity docs for advice about optimization and evmVersion

   // optimizer: {

   //  enabled: false,

   //  runs: 200

   // },

   // evmVersion: "byzantium"

   // }

  }



## npm错误

npm install



# gaslimit

https://blog.csdn.net/wo541075754/article/details/79042558

gas limit
区块gas limit是单个区块允许的最多gas总量，以此可以用来决定单个区块中能打包多少笔交易。

例如，我们有5笔交易的gas limit分别是10、20、30、40和50.如果区块gas limit是100，那么前4笔交易就能被成功打包进入这个区块。矿工有权决定将哪些交易打包入区块。所以，另一个矿工可以选择打包最后两笔交易进入这个区块（50+40），然后再将第一笔交易打包（10）。如果你尝试将一个会使用超过当前区块gas limit的交易打包，这个交易会被网络拒绝，你的以太坊客户端会反馈错误”交易超过区块gas limit”。

目前使用的gas limit为21000。

区块的gas limit是由在网络上的矿工决定的。与可调整的区块gas limit协议不同的是一个默认的挖矿策略，即大多数客户端默认最小区块gas limit为4,712,388。



## ChainId与NetworkId 

ChainId 是用来防止交易在不同的以太坊同构网络进行交易重放的。主要在交易签名和验证的时候使用。

NetworkId 是用来标识区块链网络的。主要在节点之间握手并相互检验的时候使用。

ChainId 需要在 genesis 文件中指定，NetworkId 需要在启动参数中指定。

ChainId 和 NetworkId 的值不需要相同。


链接：https://www.jianshu.com/p/b8730a05eb36

# Highcharts 基本柱形图

https://www.runoob.com/highcharts/highcharts-column-basic.html

# 在线投票系统php

https://blog.csdn.net/tianyao9hen/article/details/50495693

# solidity学习笔记

https://www.jianshu.com/p/b39a4aed3663



遍历mapping

https://www.yangtaotech.cn/post/mapping_solidity.html



## webpack加载样式

https://webpack.docschina.org/loaders/style-loader/

https://webpack.js.org/concepts/loaders/#resolving-loaders

```
npm install --save-dev css-loader ts-loader
npm install --save-dev style-loader
```

**webpack.config.js**

```js
module.exports = {
  module: {
    rules: [
      {
        test: /\.css$/i,
        use: ['style-loader', 'css-loader'],
      },
    ],
  },
};
```

## error：out of gas

https://ethereum.stackexchange.com/questions/77666/ganache-vm-exception-while-processing-transaction-out-of-gas



## Returned error: VM Exception while processing transaction: revert error

不重新开启区块链，合约是更新不了的，妈的

链不重开，合约就不能生效，改了那么多版的合约还是同样的错误提示，可不是吗？因为链上的合约还是没变，经历了一晚加一个上午才发现这个问题[em]e100[/em]