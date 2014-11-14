package com.jetbrains.lang.dart.ide.inspections.analyzer;

import com.intellij.codeInspection.HTMLComposer;
import com.intellij.codeInspection.lang.GlobalInspectionContextExtension;
import com.intellij.codeInspection.lang.HTMLComposerExtension;
import com.intellij.codeInspection.lang.InspectionExtensionsFactory;
import com.intellij.codeInspection.lang.RefManagerExtension;
import com.intellij.codeInspection.reference.RefManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

public class DartInspectionExtensionsFactory extends InspectionExtensionsFactory {
  @Override
  public GlobalInspectionContextExtension createGlobalInspectionContextExtension() {
    return new DartGlobalInspectionContext();
  }

  @Override
  public RefManagerExtension createRefManagerExtension(RefManager refManager) {
    return null;
  }

  @Override
  public HTMLComposerExtension createHTMLComposerExtension(HTMLComposer composer) {
    return null;
  }

  @Override
  public boolean isToCheckMember(@NotNull PsiElement element, @NotNull String id) {
    return true;
  }

  @Override
  public String getSuppressedInspectionIdsIn(@NotNull PsiElement element) {
    return null;
  }

  @Override
  public boolean isProjectConfiguredToRunInspections(@NotNull Project project, boolean online) {
    return true;
  }
}
