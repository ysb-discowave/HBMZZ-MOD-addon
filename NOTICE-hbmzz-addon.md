# NOTICE — hbmzz:addon

本文件说明 `hbmzz:addon` 的来源、许可证与覆盖范围。**分发本仓库的源码或二进制时，必须原样
附带本文件与 [`LICENSE-PolyForm.txt`](LICENSE-PolyForm.txt)。**

---

## 1. 许可

`hbmzz:addon` 的内容来自 **NTM Cursed Addon**，按 **PolyForm Shield License 1.0.0** 分发。
条款全文见 [`LICENSE-PolyForm.txt`](LICENSE-PolyForm.txt)，或
<https://polyformproject.org/licenses/shield/1.0.0>。

具体含义（以条款原文为准）：

- 允许阅读、修改、再分发本仓库内容；
- **不得 sublicense**：不能把本仓库内容改成 GPL-3.0、MIT 或任何其他许可再发布，
  也不能声称它适用别的许可（PolyForm Shield, "No Other Rights"）；
- 含 **noncompete** 条款：不得用本仓库内容去提供与上游软件相竞争的产品；
- 再分发时须随附条款全文或条款 URL，并保留下面两行 `Required Notice` 原文（"Notices"）。

**本仓库不是 GPL-3.0。** 根 mod（`hbmzz`）是 GPL-3.0-only，两者分开声明、分开发，
不要把两者放进同一份许可声明里——那等于替上游做了一份它没给的授权。

## 2. Required Notice（原文，不得改写）

```
Required Notice: Copyright 2025-2026 Leafia (https://github.com/ntmleafia)
Required Notice: Copyright 2025-2026 movblock <admin@movblock.mov> (https://github.com/mlbv)
```

## 3. 来源

| 项 | 值 |
|---|---|
| 上游仓库 | <https://github.com/ntmleafia/NTM-Cursed-Addon>（分支 `main`） |
| 上游作者 | Leafia、movblock 等 |
| 转为 PolyForm Shield 1.0.0 的 commit | `27ed746c`（2026-10-23） |
| 本仓库内容的抓取来源 | `D:\DSH\HBMZZ-MOD` @ `4dc1d26` 的 `src/main/resources/assets/hbmzz/`，其上游同为上述 commit 之后的快照 |
| 许可原文校验和 | `LICENSE-PolyForm.txt` sha256 `9329c686b6e7094fc8b122c76d848b164685302cb6629c6c4df83043c890ce77`（与上游快照逐字节一致） |

上游另有 `modules/lgpl-code` 与 `modules/lgpl-assets` 两个模块仍按 **LGPL-3.0** 授权。
**本仓库不包含这两个模块的任何内容。**

## 4. 覆盖范围

本仓库当前只含资源，逐个目录的来源：

| 路径 | 内容 | 归属 |
|---|---|---|
| `src/main/resources/assets/hbmzz/models/control_panel/addon/**` | 6 个 `.obj`（`lever`、`meter_vertical`、`nixie`、`starboundbuttonlol`、`starboundswitchlol`、`textindicator`） | 上游 Cursed Addon → PolyForm Shield 1.0.0 |
| `src/main/resources/assets/hbmzz/textures/models/control_panel/addon/**` | 16 张 `.png` | 同上 |
| `src/main/resources/assets/hbmzz/textures/gui/control_panel/{solid,solid_emissive,invisible}.png` | 3 张 GUI 贴图 | 同上 |
| `src/main/java/com/hbmzz/addon/**`、`build.gradle`、`mcmod.info`、`README.md`、本文件 | 本仓库自己的脚手架与说明 | HBMZZ 项目自有，随本仓库按同一许可提供 |

基础控件（非 `addon/` 子目录）的 OBJ 与贴图来自 **NTM-CE，GPL-3.0**，与 `textures/gui/control_panel/`
其余 11 张贴图一样，**不在本仓库内、也不属于本仓库的授权范围**——它们是根 mod `hbmzz` 的一部分，
随根 mod 的 jar 按 GPL-3.0 分发。本仓库刻意不复制它们，有两个原因：

1. 许可：把它们搬进来会让本仓库同时承载 GPL-3.0 与 PolyForm 两种互不兼容的声明，
   而本仓库只为 PolyForm 那部分存在；
2. 加载：两个 jar 共用 `assets/hbmzz/` 域，同名资源同时出现在两个 jar 里时，
   最终用哪一份取决于 classpath 顺序，表现为随机丢贴图。

因此「同一份资源只有一个家」是本仓库的硬边界：

| 未包含的路径 | 文件数 | 归属 |
|---|---|---|
| `assets/hbmzz/models/control_panel/*.obj`（非 `addon/`） | 13 | NTM-CE，GPL-3.0 |
| `assets/hbmzz/textures/models/control_panel/*.png`（非 `addon/`） | 22 | NTM-CE，GPL-3.0 |
| `assets/hbmzz/textures/gui/control_panel/` 其余贴图 | 11 | 见根仓库 `NOTICE.md` |
若你在这份副本里看到这些文件，说明这份快照的拆分尚未落地，见根仓库 `NOTICE.md`。

## 5. 与根 mod 的关系

- `hbmzz:addon`（modid `hbmzzaddon`）对 `hbmzz` 是**软依赖**：`after:hbmzz`，即"若存在则后加载"，
  不是 `required-after`。缺少根 mod 时本 jar 仍可加载，但它的资源没有任何东西去引用。
- 根 mod 不依赖本 jar，可以单独安装。
- 资源域固定为 `assets/hbmzz/`（**不是** `assets/hbmzzaddon/`）：注册名与贴图路径沿用拆分前的写法，
  改动会让旧存档的方块/实体指向空模型。因此**同一个资源文件只能存在于其中一个 jar 里**，
  两个 jar 都带同名资源时，加载谁取决于 classpath 顺序。
