# HBMZZ-MOD-addon

`hbmzz:addon` —— 从 [HBMZZ](https://github.com/ysb-discowave/HBMZZ-MOD) 拆出的控制台/附属内容。

这个 jar 是**根 mod 的附属包**，不能单独使用。它包含控制面板里那些来自 NTM Cursed Addon 的
仪器内容（addon 控件、节点的模型与贴图，以及控件面板的 solid/invisible GUI 贴图），这些内容的
许可证与根 mod 不同，因此单独放在一个仓库里声明、单独发一个 jar。

两边的分工：

| | 提供什么 | 许可 |
|---|---|---|
| 根 mod `hbmzz` | `com.hbmzz.cecp` 控制台框架（控件基类、节点系统、编辑器界面、方块与方块实体）、基础控件的模型与贴图 | GPL-3.0-only |
| 本包 `hbmzzaddon` | 仪器控件（11 个控件、7 个配置面板、节点）与它们的素材 | PolyForm Shield 1.0.0 |

`cecp` 是从「NTM: 诅咒版」移植的 GPL-3.0 代码，**留在根 mod**，本包不带它。

**装法：两个 jar 都要装。** 把 `hbmzz-<版本>.jar` 和 `hbmzz-addon-<版本>.jar` 一起放进 `mods/`
目录。只装 addon 不会有效果（框架在根 mod 里，没有它这些仪器无处安放）；只装根 mod 则仪器控件缺失。

## 依赖关系

- modid：`hbmzzaddon`
- **依赖方向：本包需要根 mod 在场。** 对根 mod 是软依赖：`after:hbmzz`（若根 mod 存在则在后加载），
  不是 `required-after`——缺根 mod 时本包仍能加载，但它的资源没有任何东西去引用。
  反过来根 mod 不依赖本包，可以单独安装。
- 需要 Minecraft 1.12.2 + Forge（`1.12.2-14.23.5.2847`）。
- 资源域沿用 `assets/hbmzz/`，**不是** `assets/hbmzzaddon/`：注册名与贴图路径必须和拆分前一致，
  否则旧存档里的方块与方块实体找不到模型。

## 许可

**PolyForm Shield License 1.0.0** —— 全文见 [`LICENSE-PolyForm.txt`](LICENSE-PolyForm.txt)，
来源与必须原样保留的 `Required Notice` 行见 [`NOTICE-hbmzz-addon.md`](NOTICE-hbmzz-addon.md)。

要点：可再分发、可修改；**不得 sublicense**（不能改成 GPL/MIT 等再发布）；含 noncompete 条款；
再分发须随附条款全文或 URL 及 `Required Notice` 原文。

本仓库**不是** GPL-3.0。根 mod `hbmzz` 是 GPL-3.0-only，两者分开声明、分开发，请勿混为一谈。

## 从源码构建

需要 JDK 8 与 Gradle 4.10.3（ForgeGradle 2.3 不支持更高版本的 Gradle）：

```sh
export JAVA_HOME="<jdk8>"
"$JAVA_HOME/bin/java" -Xmx3G \
  -classpath "<gradle-4.10.3>/lib/gradle-launcher-4.10.3.jar" \
  org.gradle.launcher.GradleMain build
```

产物：`build/libs/hbmzz-addon-<版本>.jar`（首次构建会下载并反编译 Minecraft，需要联网；
之后可用 `--offline`）。许可文件会被打进 jar 的 `META-INF/`。

## 目录

```
build.gradle                      ForgeGradle 2.3 / 1.12.2-14.23.5.2847
src/main/java/com/hbmzz/addon/    仅一个 @Mod 入口（当前阶段只带资源，控件代码尚未迁入）
src/main/resources/mcmod.info     modid hbmzzaddon，after:hbmzz
src/main/resources/assets/hbmzz/  models/control_panel/addon/**、
                                  textures/models/control_panel/addon/**、
                                  textures/gui/control_panel/{solid,solid_emissive,invisible}.png
LICENSE-PolyForm.txt              PolyForm Shield 1.0.0 全文（逐字节照抄上游）
NOTICE-hbmzz-addon.md             来源、commit、Required Notice、覆盖范围
```
