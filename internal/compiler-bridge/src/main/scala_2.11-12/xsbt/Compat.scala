/*
 * Zinc - The incremental compiler for Scala.
 * Copyright Lightbend, Inc. and Mark Harrah
 *
 * Licensed under Apache License 2.0
 * (http://www.apache.org/licenses/LICENSE-2.0).
 *
 * See the NOTICE file distributed with this work for
 * additional information regarding copyright ownership.
 */

package xsbt

import java.io.PrintWriter
import xsbti.compile.Output

import scala.tools.nsc.Settings

abstract class Compat
object Compat {
  // IR is renamed to Results
  val Results = scala.tools.nsc.interpreter.IR

  // IMain in 2.13 accepts ReplReporter
  def replReporter(settings: Settings, writer: PrintWriter) = writer

  type GlobalSymbolLoaders = scala.tools.nsc.GlobalSymbolLoaders
}

/** Defines compatibility utils for [[ZincCompiler]]. */
trait ZincGlobalCompat {
  self: CallbackGlobal =>
  protected def superDropRun(): Unit = ()

  final def instrumentMacroInfrastructure(callback: xsbti.AnalysisCallback): Unit = {
    analyzer.addMacroPlugin(new analyzer.MacroPlugin {
      override def pluginsMacroRuntime(expandee: Tree): Option[analyzer.MacroRuntime] = {
        callback.invokedMacro(expandee.symbol.fullName)
        None
      }

      override def pluginsTypedMacroBody(typer: analyzer.Typer, ddef: DefDef): Option[Tree] = {
        callback.definedMacro(ddef.symbol.fullName)
        None
      }
    })
  }
}

private trait CachedCompilerCompat { self: CachedCompiler0 =>
  def newCompiler(settings: Settings, reporter: DelegatingReporter, output: Output,
    cache: Option[rsc.output.InMemoryOutputCache] = None
  ): ZincCompiler =
    new ZincCompiler(settings, reporter, output, cache)
}