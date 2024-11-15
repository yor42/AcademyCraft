# AcademyCraft: Unofficial Extended Support

<div align="center">
  <img src="https://raw.githubusercontent.com/LambdaInnovation/AcademyCraft/master/blob/logo.png" alt="Project Logo">
  <p><em>A Minecraft mod about superability, inspired by <a href="https://en.wikipedia.org/wiki/A_Certain_Scientific_Railgun">A Certain Scientific Railgun (とある科学の超電磁砲)</a></em></p>
</div>

---

## 📋 Table of Contents
- [📢 Project Status](#-project-status)
- [🔄 Changes & Updates](#-changes--updates)
- [💻 Development](#-development)
- [🌐 Localization](#-localization)
- [⚠️ Donations & Project Status](#️-donations--project-status)
- [📜 Licensing](#-licensing)
- [📚 Additional Information](#-additional-information)

---

## 📢 Project Status

### Memorial Notice & Project Maintenance
This project is now **unofficially-maintained** in memory of [WeAthFoLD](https://github.com/WeAthFoLD).  
While I strive to keep it functional, **major updates are limited**. Contributors are encouraged to help maintain this legacy mod.

**This UNOFFICIAL fork** aims to:
- Keep the mod compatible with RetroFuturaGradle (RFG)
- Fix incompatibilities and bugs

---

## 🔄 Changes & Updates

### Recent Improvements
- ✅ Merged LambdaLib2 into the source
- ✅ Migrated to RetroFuturaGradle (RFG)
- ✅ Removed defunct server connectivity
- ✅ Added Groovyscript support
- ✅ Migrated RF library to Forge Energy
- ✅ Implemented proper capabilities

### Roadmap
- 🎯 Port mod to **Java 21/Scala 3**

---

## 💻 Development

### Building

To build the project, run:

```bash
./gradlew build
```
`gradlew build` will build the project and put the compiled jar in `build/libs`.  
If you encounter the Unknown constant: 18 error, simply run the command again.

## Recommended Development Environment
- **IDE**: IntelliJ IDEA
- **Scala Plugin**: Required
- **Eclipse**: Untested and unsupported

Then, open the .ipr project file to start developing.
```
./gradlew setupDecompWorkspace idea
```
then you can open the `.ipr` project file and start developing.

🌐 Localization
============

**Localization contributions are highly appreciated!**
Submit your translations via a PR to the `main` branch.

Localized content includes:
- [Lang files][langdir]
- [Tutorial texts][tutdir]

Proper credits will be given in the next release.

## ⚠️ Donations & Project Status
While we appreciate the community's support, **monetary contributions cannot be guaranteed to reach the developer's family.** Instead, we encourage you to:

- ⭐ Star this repository to honor the developer's work
- 💻 Contribute to the codebase if you find it valuable

## Historical Information & Memorial Archive

The following section is preserved in memory of the original developer, **who passed away in Summer 2021**.

### Original Donation Information (Historical)

> ⚠️ Note: These links are preserved for archival purposes only and are no longer active.

* [Development Blog & Project History (Patreon)][patreon]
* Historical Alipay QR Code:

![][alipay]

## 📜 Licensing

### AcademyCraft
AcademyCraft is licensed under [GPLv3](http://www.gnu.org/licenses/gpl.html) with the following additional terms:

#### Commercial Restrictions
- 🚫 No commercial exploitation of AcademyCraft or its content is allowed. This includes, but is not limited to:
  - Paid downloads (real/virtual currencies or tokens)
  - In-game sales of AcademyCraft items or abilities
  - Monetization of mod content in any form

#### Analytics Notice
> ⚠️ As of 2024, the analytics server is no longer operational.
**All data collection features have been permanently disabled.**
>
> Historical note: The mod previously collected anonymous usage data (level-ups, skill usage, etc.) for mod improvement purposes.

#### Rights Reserved
Lambda Innovation retains all rights to AcademyCraft, including:
- Copyright
- Right of authorship
- Ownership rights

These rights are retained regardless of agreements, and Lambda Innovation reserves the right to revoke authorizations.

### LambdaLib2
LambdaLib2 is licensed under the MIT License.

<details>
<summary>MIT License Text</summary>

```text
The MIT License (MIT)

Copyright (c) 2020 LambdaInnovation

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
```
</details>

## 📚 Additional Information
### Modpack Usage
✅ You are free to include this mod in modpacks.

### Regarding Toaru Magic Index

AcademyCraft is loosely inspired by A Certain Scientific Railgun, a spinoff of A Certain Magic Index.
The mod focuses solely on the science side of the story, building an experience centered around superabilities.

---

## 中文许可说明
所有版本的 AcademyCraft 使用 **GPLv3 协议。**

### 附加限制
- 禁止任何形式出售 AcademyCraft 及其内容，包括但不限于：
  - 付费下载 (法定货币、虚拟货币、游戏代币等)
  - 游戏内出售 AcademyCraft 物品或能力

### 数据收集说明
> ⚠️ 自2024年起，分析服务器已停止运行，所有数据收集功能已禁用。

### 权利声明
- LambdaInnovation 保留对 AcademyCraft 的著作权、署名权和最终解释权。

[langdir]: src/main/resources/assets/academy/lang
[tutdir]: src/main/resources/assets/academy/tutorials
[lambdalib2]: https://github.com/LambdaInnovation/LambdaLib2
[patreon]: https://www.patreon.com/WeAthFolD
[alipay]: https://raw.githubusercontent.com/LambdaInnovation/AcademyCraft/master/blob/qr.jpg